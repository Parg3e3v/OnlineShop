package com.parg3v.tz_effective.view.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.Product
import com.parg3v.domain.use_cases.ContainsTagUseCase
import com.parg3v.domain.use_cases.DeleteFromFavoritesUseCase
import com.parg3v.domain.use_cases.GetProductsUseCase
import com.parg3v.domain.use_cases.ProductsListWithFavoritesUseCase
import com.parg3v.domain.use_cases.SaveToFavoritesUseCase
import com.parg3v.domain.use_cases.SortProductsByPopularityUseCase
import com.parg3v.domain.use_cases.SortProductsByPriceToMaxUseCase
import com.parg3v.domain.use_cases.SortProductsByPriceToMinUseCase
import com.parg3v.tz_effective.model.ProductsListState
import com.parg3v.tz_effective.model.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val sortProductsByPopularityUseCase: SortProductsByPopularityUseCase,
    private val sortProductsByPriceToMinUseCase: SortProductsByPriceToMinUseCase,
    private val sortProductsByPriceToMaxUseCase: SortProductsByPriceToMaxUseCase,
    private val containsTagUseCase: ContainsTagUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val productsListWithFavoritesUseCase: ProductsListWithFavoritesUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsListState())
    val productsState: StateFlow<ProductsListState> = _productsState.asStateFlow()

    private val _filteredProductsState = MutableStateFlow(ProductsListState())
    val filteredProductsState: StateFlow<ProductsListState> = _filteredProductsState.asStateFlow()

    private val _selectedOption = MutableStateFlow("all")
    val selectedOption: StateFlow<String> = _selectedOption.asStateFlow()


    fun getProducts() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _productsState.value = ProductsListState(data = result.data.orEmpty())
                    sortBy(SortType.POPULARITY)
                }

                is ResultOf.Failure -> {
                    _productsState.value = ProductsListState(
                        error = result.message ?: "Unexpected Error"
                    )
                }

                is ResultOf.Loading -> {
                    _productsState.value = ProductsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sortBy(field: SortType) {
        viewModelScope.launch {
            if (_selectedOption.value == "all") {
                _productsState.value = when (field) {
                    SortType.POPULARITY -> {
                        ProductsListState(data = sortProductsByPopularityUseCase(_productsState.value.data))
                    }

                    SortType.PRICE_TO_MAX -> {
                        ProductsListState(data = sortProductsByPriceToMaxUseCase(_productsState.value.data))
                    }

                    SortType.PRICE_TO_MIN -> {
                        ProductsListState(data = sortProductsByPriceToMinUseCase(_productsState.value.data))
                    }
                }
            } else {
                _filteredProductsState.value = when (field) {
                    SortType.POPULARITY -> {
                        ProductsListState(
                            data = sortProductsByPopularityUseCase(
                                _filteredProductsState.value.data
                            )
                        )
                    }

                    SortType.PRICE_TO_MAX -> {
                        ProductsListState(
                            data = sortProductsByPriceToMaxUseCase(
                                _filteredProductsState.value.data
                            )
                        )
                    }

                    SortType.PRICE_TO_MIN -> {
                        ProductsListState(
                            data = sortProductsByPriceToMinUseCase(
                                _filteredProductsState.value.data
                            )
                        )
                    }
                }
            }
        }
    }

    fun containsTag(tag: String) {
        viewModelScope.launch {
            _filteredProductsState.value =
                ProductsListState(data = containsTagUseCase(_productsState.value.data, tag))
            _selectedOption.value = tag
        }
    }

    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            saveToFavoritesUseCase(product)

            withContext(Dispatchers.Main) {
                matchProductsListWithLocalData()
            }
        }
    }

    fun deleteFromFavorites(product: Product) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(product)

            withContext(Dispatchers.Main) {
                matchProductsListWithLocalData()
            }
        }
    }

    fun matchProductsListWithLocalData() {
        if (_selectedOption.value == "all") {
            productsListWithFavoritesUseCase(_productsState.value.data).onEach { result ->
                when (result) {
                    is ResultOf.Failure -> {
                        _productsState.value = ProductsListState(
                            error = result.message ?: "Unexpected Error"
                        )
                    }

                    is ResultOf.Loading -> {}

                    is ResultOf.Success<*> -> {
                        _productsState.value = ProductsListState(data = result.data.orEmpty())
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            productsListWithFavoritesUseCase(_filteredProductsState.value.data).onEach { result ->
                when (result) {
                    is ResultOf.Failure -> {
                        _filteredProductsState.value = ProductsListState(
                            error = result.message ?: "Unexpected Error"
                        )
                    }

                    is ResultOf.Loading -> {}

                    is ResultOf.Success<*> -> {
                        _filteredProductsState.value = ProductsListState(data = result.data.orEmpty())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
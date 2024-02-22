package com.parg3v.tz_effective.view.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.Product
import com.parg3v.domain.use_cases.ContainsTagUseCase
import com.parg3v.domain.use_cases.DeleteFromFavoritesUseCase
import com.parg3v.domain.use_cases.IsFavoriteProductUseCase
import com.parg3v.domain.use_cases.GetProductsUseCase
import com.parg3v.domain.use_cases.SaveToFavoritesUseCase
import com.parg3v.domain.use_cases.SortProductsByPopularityUseCase
import com.parg3v.domain.use_cases.SortProductsByPriceToMaxUseCase
import com.parg3v.domain.use_cases.SortProductsByPriceToMinUseCase
import com.parg3v.tz_effective.model.ProductsListState
import com.parg3v.tz_effective.model.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val isFavoriteProductUseCase: IsFavoriteProductUseCase
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
                    _productsState.value =
                        ProductsListState(data = updateFavoriteProducts(result.data.orEmpty()))
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
                    ProductsListState(data = sortProductsByPopularityUseCase(_filteredProductsState.value.data))
                }

                SortType.PRICE_TO_MAX -> {
                    ProductsListState(data = sortProductsByPriceToMaxUseCase(_filteredProductsState.value.data))
                }

                SortType.PRICE_TO_MIN -> {
                    ProductsListState(data = sortProductsByPriceToMinUseCase(_filteredProductsState.value.data))
                }
            }
        }
    }

    fun containsTag(tag: String) {
        _filteredProductsState.value =
            ProductsListState(data = containsTagUseCase(_productsState.value.data, tag))
        _selectedOption.value = tag
    }

    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            saveToFavoritesUseCase(product)
            _productsState.value =
                ProductsListState(data = updateFavoriteProducts(_productsState.value.data))
        }
    }

    fun deleteFromFavorites(product: Product) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(product)
            _productsState.update { ProductsListState(data = updateFavoriteProducts(it.data)) }
        }
    }

    private suspend fun updateFavoriteProducts(products: List<Product>): List<Product> {
        return products.onEach { product ->
            product.isFavorite = isFavoriteProductUseCase(product.id)
        }
    }
}
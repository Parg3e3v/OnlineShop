package com.parg3v.tz_effective.view.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.use_cases.GetProductsUseCase
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
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val sortProductsByPopularityUseCase: SortProductsByPopularityUseCase,
    private val sortProductsByPriceToMinUseCase: SortProductsByPriceToMinUseCase,
    private val sortProductsByPriceToMaxUseCase: SortProductsByPriceToMaxUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsListState())
    val productsState: StateFlow<ProductsListState> = _productsState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _productsState.value = ProductsListState(data = result.data.orEmpty())
                }

                is ResultOf.Failure -> {
                    _productsState.value =
                        ProductsListState(
                            error = result.message ?: "Unexpected Error"
                        )
                }

                is ResultOf.Loading -> {
                    _productsState.value =
                        ProductsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sortBy(field: SortType) {
        when (field) {
            SortType.POPULARITY -> {
                _productsState.value =
                    ProductsListState(data = sortProductsByPopularityUseCase(_productsState.value.data))
            }

            SortType.PRICE_TO_MAX -> {
                _productsState.value =
                    ProductsListState(data = sortProductsByPriceToMaxUseCase(_productsState.value.data))
            }

            SortType.PRICE_TO_MIN -> {
                _productsState.value =
                    ProductsListState(data = sortProductsByPriceToMinUseCase(_productsState.value.data))
            }
        }
    }
}
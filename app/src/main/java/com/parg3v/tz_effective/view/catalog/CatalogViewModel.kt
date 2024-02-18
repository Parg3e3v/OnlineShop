package com.parg3v.tz_effective.view.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.use_cases.GetProductsUseCase
import com.parg3v.tz_effective.model.ProductsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
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
}
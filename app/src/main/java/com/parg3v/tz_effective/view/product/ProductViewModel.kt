package com.parg3v.tz_effective.view.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.Product
import com.parg3v.domain.use_cases.DeleteFromFavoritesUseCase
import com.parg3v.domain.use_cases.GetProductByIdUseCase
import com.parg3v.domain.use_cases.ProductsListWithFavoritesUseCase
import com.parg3v.domain.use_cases.SaveToFavoritesUseCase
import com.parg3v.tz_effective.model.ProductState
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
class ProductViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val productsListWithFavoritesUseCase: ProductsListWithFavoritesUseCase
) : ViewModel() {
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState.asStateFlow()
    fun getProductById(id: String) {
        getProductByIdUseCase(id).onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _productState.value = ProductState(data = result.data)
                    matchWithLocalDB()
                }

                is ResultOf.Failure -> {
                    _productState.value = ProductState(
                        error = result.message ?: "Unexpected Error"
                    )
                }

                is ResultOf.Loading -> {
                    _productState.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            saveToFavoritesUseCase(product)

            withContext(Dispatchers.Main) {
                matchWithLocalDB()
            }
        }
    }

    fun deleteFromFavorites(product: Product) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(product)

            withContext(Dispatchers.Main) {
                matchWithLocalDB()
            }
        }
    }

    private fun matchWithLocalDB(){
        productsListWithFavoritesUseCase(listOf(_productState.value.data!!)).onEach { result ->
            when (result) {
                is ResultOf.Failure -> {
                    _productState.value = ProductState(
                        error = result.message ?: "Unexpected Error"
                    )
                }

                is ResultOf.Loading -> {}

                is ResultOf.Success<*> -> {
                    _productState.value = ProductState(data = result.data?.get(0))
                }
            }
        }.launchIn(viewModelScope)
    }
}
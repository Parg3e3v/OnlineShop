package com.parg3v.tz_effective.view.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.Product
import com.parg3v.domain.use_cases.DeleteFromFavoritesUseCase
import com.parg3v.domain.use_cases.GetFavoriteProductsUseCase
import com.parg3v.domain.use_cases.ProductsListWithFavoritesUseCase
import com.parg3v.domain.use_cases.SaveToFavoritesUseCase
import com.parg3v.tz_effective.model.ProductsListState
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
class FavoriteProductsViewModel @Inject constructor(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val productsListWithFavoritesUseCase: ProductsListWithFavoritesUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsListState())
    val productsState: StateFlow<ProductsListState> = _productsState.asStateFlow()

    fun getProducts() {
        getFavoriteProductsUseCase().onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _productsState.value = ProductsListState(data = result.data.orEmpty())
                    matchProductsListWithLocalData()
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

    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            saveToFavoritesUseCase(product)

            withContext(Dispatchers.Main) {
                getProducts()
            }
        }
    }

    fun deleteFromFavorites(product: Product) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(product)

            withContext(Dispatchers.Main) {
                getProducts()
            }
        }
    }

    private fun matchProductsListWithLocalData() {
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
    }
}
package com.parg3v.tz_effective.view.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.Product
import com.parg3v.domain.use_cases.DeleteFromFavoritesUseCase
import com.parg3v.domain.use_cases.GetFavoriteProductsUseCase
import com.parg3v.domain.use_cases.IsFavoriteProductUseCase
import com.parg3v.domain.use_cases.SaveToFavoritesUseCase
import com.parg3v.tz_effective.model.ProductsListState
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
class FavoriteProductsViewModel @Inject constructor(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val isFavoriteProductUseCase: IsFavoriteProductUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsListState())
    val productsState: StateFlow<ProductsListState> = _productsState.asStateFlow()

    fun getProducts() {
        getFavoriteProductsUseCase().onEach { result ->
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
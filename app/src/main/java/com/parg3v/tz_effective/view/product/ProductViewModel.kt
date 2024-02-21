package com.parg3v.tz_effective.view.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.use_cases.GetProductByIdUseCase
import com.parg3v.tz_effective.model.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
) : ViewModel() {
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState.asStateFlow()
    fun getProductById(id: String) {
        getProductByIdUseCase(id).onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _productState.value = ProductState(data = result.data)
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
}
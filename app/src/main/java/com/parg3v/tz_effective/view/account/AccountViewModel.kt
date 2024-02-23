package com.parg3v.tz_effective.view.account

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.use_cases.DeleteLoginInfoUseCase
import com.parg3v.domain.use_cases.GetFavoriteProductsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val deleteLoginInfoUseCase: DeleteLoginInfoUseCase,
    private val getFavoriteProductsCountUseCase: GetFavoriteProductsCountUseCase
) : ViewModel() {

    private val _productsCount = MutableStateFlow(0)
    val productsCount = _productsCount.asStateFlow()
    suspend fun deleteLoginInfo(context: Context) {
        deleteLoginInfoUseCase(context)
    }

    fun getFavoriteProductsCount(){
        getFavoriteProductsCountUseCase().onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _productsCount.value = result.data ?: 0
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}
package com.parg3v.tz_effective.view.account

import android.content.Context
import androidx.lifecycle.ViewModel
import com.parg3v.domain.use_cases.DeleteLoginInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val deleteLoginInfoUseCase: DeleteLoginInfoUseCase
) : ViewModel() {
    suspend fun deleteLoginInfo(context: Context) {
        deleteLoginInfoUseCase(context)
    }
}
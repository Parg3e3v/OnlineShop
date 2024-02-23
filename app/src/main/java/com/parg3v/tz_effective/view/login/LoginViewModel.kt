package com.parg3v.tz_effective.view.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.LoginInfo
import com.parg3v.domain.use_cases.GetLoginInfoUseCase
import com.parg3v.domain.use_cases.SaveLoginInfoUseCase
import com.parg3v.domain.use_cases.ValidateNameUseCase
import com.parg3v.domain.use_cases.ValidatePhoneUseCase
import com.parg3v.tz_effective.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val saveLoginInfoUseCase: SaveLoginInfoUseCase,
    private val getLoginInfoUseCase: GetLoginInfoUseCase
) : ViewModel() {

    private val _loginInfoState = MutableStateFlow(LoginState())
    val loginInfoState: StateFlow<LoginState> = _loginInfoState.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _surnameState = MutableStateFlow("")
    val surnameState: StateFlow<String> = _surnameState.asStateFlow()

    private val _phoneState = MutableStateFlow("")
    val phoneState: StateFlow<String> = _phoneState.asStateFlow()

    private val _validNameState = MutableStateFlow(false)
    val validNameState: StateFlow<Boolean> = _validNameState.asStateFlow()

    private val _validSurnameState = MutableStateFlow(false)
    val validSurnameState: StateFlow<Boolean> = _validSurnameState.asStateFlow()

    private val _validPhoneState = MutableStateFlow(false)
    val validPhoneState: StateFlow<Boolean> = _validPhoneState.asStateFlow()

    fun validateName(input: String) {
        _nameState.update { input }
        _validNameState.update { validateNameUseCase(input) && input.isNotBlank() }
    }

    fun validateSurname(input: String) {
        _surnameState.update { input }
        _validSurnameState.update { validateNameUseCase(input) && input.isNotBlank() }
    }

    fun validatePhone(input: String) {
        if (validatePhoneUseCase(input))
            _phoneState.update { input }
        _validPhoneState.update { _phoneState.value.length == 10 }
    }

    fun saveLoginInfo(context: Context) {
        saveLoginInfoUseCase(
            context = context,
            loginInfo = LoginInfo(
                name = _nameState.value,
                surname = _surnameState.value,
                phone = _phoneState.value
            )
        ).onEach { result ->
            when (result) {
                is ResultOf.Success<*> -> {
                    _loginInfoState.value = LoginState(data = result.data)
                }

                is ResultOf.Failure -> {
                    _loginInfoState.value =
                        LoginState(
                            error = result.message ?: "Unexpected Error"
                        )
                }

                is ResultOf.Loading -> {
                    _loginInfoState.value =
                        LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
        _nameState.value = ""
        _surnameState.value = ""
        _phoneState.value = ""
    }

    suspend fun getLoginInfo(context: Context){
        _loginInfoState.value = LoginState(data = getLoginInfoUseCase(context))
    }
}
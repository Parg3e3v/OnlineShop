package com.parg3v.tz_effective.view.login

import androidx.lifecycle.ViewModel
import com.parg3v.domain.use_cases.ValidateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase
) : ViewModel() {

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _surnameState = MutableStateFlow("")
    val surnameState: StateFlow<String> = _surnameState.asStateFlow()

    private val _phoneState = MutableStateFlow("")
    val phoneState: StateFlow<String> = _phoneState.asStateFlow()

    fun validateName(input: String): Boolean = if (validateNameUseCase(input)) {
        _nameState.update { input }
        true
    } else false

    fun validateSurname(input: String): Boolean = if (validateNameUseCase(input)) {
        _surnameState.update { input }
        true
    } else false

    fun validatePhone(input: String): Boolean = if (input.length <= 10) {
        _phoneState.update { input }
        true
    } else false
}
package com.parg3v.tz_effective.model

import com.parg3v.domain.model.LoginInfo

data class LoginState(
    val isLoading: Boolean = false,
    val data: LoginInfo? = LoginInfo(),
    val error: String = ""
)
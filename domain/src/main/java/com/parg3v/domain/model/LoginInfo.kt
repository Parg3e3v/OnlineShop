package com.parg3v.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
    val name: String? = null,
    val surname: String? = null,
    val phone: String? = null
)
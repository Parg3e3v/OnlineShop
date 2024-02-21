package com.parg3v.tz_effective.model

import com.parg3v.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val data: Product? = null,
    val error: String = ""
)
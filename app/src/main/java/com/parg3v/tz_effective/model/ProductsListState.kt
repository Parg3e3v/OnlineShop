package com.parg3v.tz_effective.model

import com.parg3v.domain.model.Product

data class ProductsListState(
    val isLoading: Boolean = false,
    val data: List<Product> = emptyList(),
    val error: String = ""
)
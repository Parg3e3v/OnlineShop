package com.parg3v.domain.repository

import com.parg3v.domain.model.ProductList

interface ProductsRepository {
    suspend fun getProducts(): ProductList
}
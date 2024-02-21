package com.parg3v.domain.repository

import com.parg3v.domain.model.Product
import com.parg3v.domain.model.ProductList

interface ProductsRepository {
    suspend fun getProducts(): ProductList
    suspend fun getProductById(id: String): Product?
}
package com.parg3v.domain.repository

import com.parg3v.domain.model.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun getFavoriteProducts(): List<Product>
    suspend fun getFavoriteProductsCount(): Int
    suspend fun isFavorite(productId: String): Boolean
    suspend fun saveToFavoriteProducts(product: Product)
    suspend fun deleteFromFavorites(product: Product)
}
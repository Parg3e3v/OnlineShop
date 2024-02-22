package com.parg3v.data.repository

import com.parg3v.data.local.dao.ProductsDao
import com.parg3v.data.mappers.toProduct
import com.parg3v.data.mappers.toProductEntity
import com.parg3v.data.remote.Api
import com.parg3v.domain.model.Product
import com.parg3v.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: Api,
    private val productsDao: ProductsDao
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> =
        api.getAllProducts().items.map { it.toProduct() }

    override suspend fun getProductById(id: String): Product? =
        api.getAllProducts().items.find { it.id == id }?.toProduct()

    override suspend fun getFavoriteProducts(): List<Product> =
        productsDao.getProducts().map { it.toProduct() }

    override suspend fun isFavorite(productId: String): Boolean =
        productsDao.isFavorite(productId)


    override suspend fun saveToFavoriteProducts(product: Product) {
        productsDao.insertProduct(product.toProductEntity())
    }

    override suspend fun deleteFromFavorites(product: Product) {
        productsDao.deleteProduct(product.toProductEntity())
    }

}
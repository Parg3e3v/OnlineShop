package com.parg3v.data.repository

import com.parg3v.data.extensions.toProduct
import com.parg3v.data.remote.Api
import com.parg3v.domain.model.Product
import com.parg3v.domain.model.ProductList
import com.parg3v.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: Api
) : ProductsRepository {

    override suspend fun getProducts(): ProductList =
        ProductList(api.getAllProducts().items.map { it.toProduct() })

    override suspend fun getProductById(id: String): Product? =
        api.getAllProducts().items.find { it.id == id}?.toProduct()
}
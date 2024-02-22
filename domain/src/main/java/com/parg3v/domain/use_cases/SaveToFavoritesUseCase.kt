package com.parg3v.domain.use_cases

import com.parg3v.domain.model.Product
import com.parg3v.domain.repository.ProductsRepository

class SaveToFavoritesUseCase(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(product: Product) {
        productsRepository.saveToFavoriteProducts(product)
    }
}
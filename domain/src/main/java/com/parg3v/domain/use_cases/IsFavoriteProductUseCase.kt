package com.parg3v.domain.use_cases

import com.parg3v.domain.repository.ProductsRepository
import java.io.IOException
import javax.inject.Inject

class IsFavoriteProductUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(productId: String): Boolean =
        try {
            productsRepository.isFavorite(productId)
        } catch (e: IOException) {
            false
        }
}
package com.parg3v.domain.use_cases

import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.model.Product
import com.parg3v.domain.repository.ProductsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(): Flow<ResultOf<List<Product>>> = flow {
        try {
            emit(ResultOf.Loading())
            delay(1000) // For shimmer to be shown
            val products = productsRepository.getFavoriteProducts()
            emit(ResultOf.Success(products))
        } catch (e: IOException) {
            emit(ResultOf.Failure("Couldn't get products"))
        }
    }
}
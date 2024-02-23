package com.parg3v.domain.use_cases

import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetFavoriteProductsCountUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(): Flow<ResultOf<Int>> = flow {
        try {
            emit(ResultOf.Loading())
            val products = productsRepository.getFavoriteProductsCount()
            emit(ResultOf.Success(products))
        } catch (e: IOException) {
            emit(ResultOf.Failure("Couldn't get the count of products"))
        }
    }
}
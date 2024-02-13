package com.parg3v.domain.module

import com.parg3v.domain.repository.ProductsRepository
import com.parg3v.domain.use_cases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    @Singleton
    fun provideGetProductsUseCase(productsRepository: ProductsRepository): GetProductsUseCase {
        return GetProductsUseCase(productsRepository)
    }
}
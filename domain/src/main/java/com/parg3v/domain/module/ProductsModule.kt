package com.parg3v.domain.module

import com.parg3v.domain.repository.ProductsRepository
import com.parg3v.domain.use_cases.GetProductsUseCase
import com.parg3v.domain.use_cases.ContainsTagUseCase
import com.parg3v.domain.use_cases.GetProductByIdUseCase
import com.parg3v.domain.use_cases.SortProductsByPopularityUseCase
import com.parg3v.domain.use_cases.SortProductsByPriceToMaxUseCase
import com.parg3v.domain.use_cases.SortProductsByPriceToMinUseCase
import com.parg3v.domain.use_cases.ValidateNameUseCase
import com.parg3v.domain.use_cases.ValidatePhoneUseCase
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

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(productsRepository: ProductsRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(productsRepository)
    }

    @Provides
    @Singleton
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase()
    }

    @Provides
    @Singleton
    fun provideValidatePhoneUseCase(): ValidatePhoneUseCase{
        return ValidatePhoneUseCase()
    }

    @Provides
    @Singleton
    fun provideSortProductsByPopularityUseCase(): SortProductsByPopularityUseCase{
        return SortProductsByPopularityUseCase()
    }

    @Provides
    @Singleton
    fun provideSortProductsByPriceToMaxUseCase(): SortProductsByPriceToMaxUseCase{
        return SortProductsByPriceToMaxUseCase()
    }

    @Provides
    @Singleton
    fun provideSortProductsByPriceToMinUseCase(): SortProductsByPriceToMinUseCase{
        return SortProductsByPriceToMinUseCase()
    }

    @Provides
    @Singleton
    fun provideContainsTagUseCase(): ContainsTagUseCase {
        return ContainsTagUseCase()
    }
}
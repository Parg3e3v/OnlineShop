package com.parg3v.data.di.module

import com.parg3v.data.repository.ProductsRepositoryImpl
import com.parg3v.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        repository: ProductsRepositoryImpl
    ): ProductsRepository

}
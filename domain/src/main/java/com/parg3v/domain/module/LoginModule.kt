package com.parg3v.domain.module

import com.parg3v.domain.use_cases.DeleteLoginInfoUseCase
import com.parg3v.domain.use_cases.GetLoginInfoUseCase
import com.parg3v.domain.use_cases.SaveLoginInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideSaveLoginInfoUseCase(): SaveLoginInfoUseCase {
        return SaveLoginInfoUseCase()
    }

    @Provides
    @Singleton
    fun provideGetLoginInfoUseCase(): GetLoginInfoUseCase {
        return GetLoginInfoUseCase()
    }

    @Provides
    @Singleton
    fun provideDeleteLoginInfoUseCase(): DeleteLoginInfoUseCase {
        return DeleteLoginInfoUseCase()
    }
}
package com.example.kilt.di

import com.example.kilt.network.ApiService
import com.example.kilt.repository.ConfigRepository
import com.example.kilt.repository.ConfigRepositoryImpl
import com.example.kilt.repository.HomeSaleRepository
import com.example.kilt.repository.HomeSaleRepositoryImpl
import com.example.kilt.repository.SearchRepository
import com.example.kilt.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideConfigRepository(apiService: ApiService): ConfigRepository {
        return ConfigRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideSearchRepository(apiService: ApiService): SearchRepository {
        return SearchRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideHomeSaleRepository(apiService: ApiService): HomeSaleRepository {
        return HomeSaleRepositoryImpl(apiService)
    }



}
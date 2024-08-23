package com.example.kilt.di

import com.example.kilt.network.ApiService
import com.example.kilt.repository.ConfigRepository
import com.example.kilt.repository.ConfigRepositoryImpl
import com.example.kilt.repository.FlatRepository
import com.example.kilt.repository.FlatRepositoryImpl
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
    fun provideFlatRepository(): FlatRepository {
        return FlatRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideConfigRepository(apiService: ApiService, flatRepository: FlatRepository): ConfigRepository {
        return ConfigRepositoryImpl(apiService, flatRepository)
    }
}
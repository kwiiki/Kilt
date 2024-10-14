package com.example.kilt.di

import com.example.kilt.network.ApiService
import com.example.kilt.repository.ConfigHelper
import com.example.kilt.repository.ConfigRepository
import com.example.kilt.repository.ConfigRepositoryImpl
import com.example.kilt.repository.HomeSaleRepository
import com.example.kilt.repository.HomeSaleRepositoryImpl
import com.example.kilt.repository.KatoRepository
import com.example.kilt.repository.KatoRepositoryImpl
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.LoginRepositoryImpl
import com.example.kilt.repository.RegistrationRepository
import com.example.kilt.repository.RegistrationRepositoryImpl
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
    fun provideConfigHelper(): ConfigHelper {
        return ConfigHelper()
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        apiService: ApiService,
        configRepository: ConfigRepository,
        configHelper: ConfigHelper // ConfigHelper injected here
    ): SearchRepository {
        return SearchRepositoryImpl(apiService, configRepository, configHelper)
    }

    @Provides
    @Singleton
    fun provideHomeSaleRepository(apiService: ApiService): HomeSaleRepository {
        return HomeSaleRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideKatoRepository(apiService: ApiService): KatoRepository{
        return KatoRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(apiService: ApiService): RegistrationRepository {
        return RegistrationRepositoryImpl(apiService)
    }
}
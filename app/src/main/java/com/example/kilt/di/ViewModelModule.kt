package com.example.kilt.di

import android.app.Application
import android.content.Context
import com.example.kilt.repository.ConfigRepository
import com.example.kilt.repository.HomeSaleRepository
import com.example.kilt.repository.KatoRepository
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.SearchRepository
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.LoginViewModel
import com.example.kilt.viewmodels.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideSearchViewModel(
        searchRepository: SearchRepository,
    ): SearchViewModel {
        return SearchViewModel(searchRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideHomeSaleViewModel(
        homeSaleRepository: HomeSaleRepository,
        configRepository: ConfigRepository
    ): HomeSaleViewModel {
        return HomeSaleViewModel(homeSaleRepository, configRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideChooseViewModel(
        katoRepository: KatoRepository
    ): ChooseCityViewModel {
        return ChooseCityViewModel(katoRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideLoginViewModel(
        loginRepository: LoginRepository,
        @ApplicationContext context: Context
    ): LoginViewModel {
        return LoginViewModel(loginRepository, context.applicationContext as Application)
    }
}
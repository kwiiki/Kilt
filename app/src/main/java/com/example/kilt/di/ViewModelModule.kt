package com.example.kilt.di

import android.content.Context
import com.example.kilt.data.dataStore.UserDataStoreManager
import com.example.kilt.data.shardePrefernce.PreferencesHelper
import com.example.kilt.otp.SmsViewModel
import com.example.kilt.repository.ConfigRepository
import com.example.kilt.repository.HomeSaleRepository
import com.example.kilt.repository.KatoRepository
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.RegistrationRepository
import com.example.kilt.repository.SearchRepository
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.AuthViewModel
import com.example.kilt.viewmodels.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

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
    fun provideAuthViewModel(
        registrationRepository: RegistrationRepository,
        loginRepository: LoginRepository,
        userDataStoreManager: UserDataStoreManager,
        preferencesHelper: PreferencesHelper
    ): AuthViewModel {
        return AuthViewModel(registrationRepository, loginRepository, userDataStoreManager,preferencesHelper)
    }

    @Provides
    @ViewModelScoped
    fun provideSmsViewModel(
    ): SmsViewModel {
        return SmsViewModel()
    }

}
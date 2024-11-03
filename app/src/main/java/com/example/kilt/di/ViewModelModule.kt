package com.example.kilt.di

import android.content.Context
import com.example.kilt.models.dataStore.UserDataStoreManager
import com.example.kilt.models.shardePrefernce.PreferencesHelper
import com.example.kilt.otp.SmsViewModel
import com.example.kilt.domain.config.repository.ConfigRepository
import com.example.kilt.domain.edit_profile.repository.EditProfileRepository
import com.example.kilt.repository.HomeSaleRepository
import com.example.kilt.repository.IdentificationRepository
import com.example.kilt.repository.KatoRepository
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.RegistrationRepository
import com.example.kilt.repository.SearchRepository
import com.example.kilt.repository.UserRepository
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.AuthViewModel
import com.example.kilt.viewmodels.EditProfileViewModel
import com.example.kilt.viewmodels.IdentificationViewModel
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
    fun provideAuthViewModel(
        registrationRepository: RegistrationRepository,
        loginRepository: LoginRepository,
        identificationRepository: IdentificationRepository,
        userRepository: UserRepository,
        userDataStoreManager: UserDataStoreManager,
        preferencesHelper: PreferencesHelper,
    ): AuthViewModel {
        return AuthViewModel(
            registrationRepository,
            loginRepository,
            identificationRepository,
            userRepository,
            userDataStoreManager,
            preferencesHelper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEditProfileViewModel(editProfileRepository: EditProfileRepository): EditProfileViewModel {
        return EditProfileViewModel(editProfileRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideIdentificationViewModel(
        @ApplicationContext context: Context,
        identificationRepository: IdentificationRepository,
        configRepository: ConfigRepository,
        userDataStoreManager: UserDataStoreManager,
        preferencesHelper: PreferencesHelper
    ): IdentificationViewModel {
        return IdentificationViewModel(
            context,
            identificationRepository,
            configRepository,
            userDataStoreManager,
            preferencesHelper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSmsViewModel(
    ): SmsViewModel {
        return SmsViewModel()
    }

}
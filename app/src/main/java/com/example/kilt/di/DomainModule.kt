package com.example.kilt.di

import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.AddPhoneUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UniversalUserCreateUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UserFindByOTPUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideAddPhoneUseCase(repository: AddNewPhoneNumberRepository): AddPhoneUseCase {
        return AddPhoneUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUserFindByOTPUser(repository: AddNewPhoneNumberRepository): UserFindByOTPUseCase {
        return UserFindByOTPUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUniversalUserCreate(repository: AddNewPhoneNumberRepository) : UniversalUserCreateUseCase {
        return UniversalUserCreateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUserIdUseCase(userDataStoreManager: UserDataStoreManager) : GetUserIdUseCase{
        return GetUserIdUseCase(userDataStoreManager)
    }
}
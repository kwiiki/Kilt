package com.example.kilt.di

import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.domain.choosecity.usecase.GetKatoByIdUseCase
import com.example.kilt.domain.choosecity.usecase.GetMicroDistrictByIdUseCase
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.AddPhoneUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UniversalUserCreateUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UserFindByOTPUseCase
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import com.example.kilt.domain.editprofile.usecase.AddNewImageUseCase
import com.example.kilt.domain.editprofile.usecase.DeleteImageUseCase
import com.example.kilt.domain.editprofile.usecase.GetUserPhoneNumbersUseCase
import com.example.kilt.repository.KatoRepository
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

    @Singleton
    @Provides
    fun provideGetUserUseCase(userDataStoreManager: UserDataStoreManager):GetUserUseCase{
        return GetUserUseCase(userDataStoreManager)
    }
    @Singleton
    @Provides
    fun provideGetUserPhoneNumberUseCase(getUserIdUseCase: GetUserIdUseCase,repository:EditProfileRepository) : GetUserPhoneNumbersUseCase{
        return GetUserPhoneNumbersUseCase(getUserIdUseCase = getUserIdUseCase, repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetKatoById(katoRepository: KatoRepository):GetKatoByIdUseCase{
        return GetKatoByIdUseCase(katoRepository = katoRepository)
    }

    @Singleton
    @Provides
    fun provideGetMicroDistrictByIdUseCase(katoRepository: KatoRepository):GetMicroDistrictByIdUseCase{
        return GetMicroDistrictByIdUseCase(katoRepository)
    }

    @Provides
    @Singleton
    fun provideAddNewImageUseCase(
        repository: EditProfileRepository
    ): AddNewImageUseCase {
        return AddNewImageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteImageUseCase(repository: EditProfileRepository):DeleteImageUseCase{
        return DeleteImageUseCase(repository)
    }

}
package com.example.kilt.di

import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.domain.choosecity.usecase.GetFullAddressUseCase
import com.example.kilt.domain.choosecity.usecase.GetKatoByIdUseCase
import com.example.kilt.domain.choosecity.usecase.GetMicroDistrictByIdUseCase
import com.example.kilt.domain.choosecity.usecase.LocationSaverUseCase
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.AddPhoneUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.DeleteSecondPhoneNumberUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UniversalUserCreateUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UserFindByOTPUseCase
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import com.example.kilt.domain.editprofile.usecase.AddNewImageUseCase
import com.example.kilt.domain.editprofile.usecase.ChangeUserTypeUseCase
import com.example.kilt.domain.editprofile.usecase.DeleteImageUseCase
import com.example.kilt.domain.editprofile.usecase.GetUserPhoneNumbersUseCase
import com.example.kilt.domain.editprofile.usecase.UserUpdateUseCase
import com.example.kilt.domain.profile.repository.ProfileRepository
import com.example.kilt.domain.profile.usecase.CheckUserModerationStatusUseCase
import com.example.kilt.domain.userabout.repository.UserAboutRepository
import com.example.kilt.domain.userabout.usecase.GetUserListingsUseCase
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
    fun provideUniversalUserCreate(repository: AddNewPhoneNumberRepository): UniversalUserCreateUseCase {
        return UniversalUserCreateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUserIdUseCase(userDataStoreManager: UserDataStoreManager): GetUserIdUseCase {
        return GetUserIdUseCase(userDataStoreManager)
    }

    @Singleton
    @Provides
    fun provideGetUserUseCase(userDataStoreManager: UserDataStoreManager): GetUserUseCase {
        return GetUserUseCase(userDataStoreManager)
    }

    @Singleton
    @Provides
    fun provideGetUserPhoneNumberUseCase(
        getUserIdUseCase: GetUserIdUseCase,
        repository: EditProfileRepository
    ): GetUserPhoneNumbersUseCase {
        return GetUserPhoneNumbersUseCase(
            getUserIdUseCase = getUserIdUseCase,
            repository = repository
        )
    }

    @Singleton
    @Provides
    fun provideGetKatoById(katoRepository: KatoRepository): GetKatoByIdUseCase {
        return GetKatoByIdUseCase(katoRepository = katoRepository)
    }

    @Singleton
    @Provides
    fun provideGetMicroDistrictByIdUseCase(katoRepository: KatoRepository): GetMicroDistrictByIdUseCase {
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
    fun provideDeleteImageUseCase(repository: EditProfileRepository): DeleteImageUseCase {
        return DeleteImageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserListingsUseCase(
        repository: UserAboutRepository,
        getUserIdUseCase: GetUserIdUseCase
    ): GetUserListingsUseCase {
        return GetUserListingsUseCase(repository, getUserIdUseCase)
    }

    @Provides
    @Singleton
    fun provideDeleteSecondPhoneNumberUseCase(repository: AddNewPhoneNumberRepository): DeleteSecondPhoneNumberUseCase {
        return DeleteSecondPhoneNumberUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFullAddressUseCase(locationSaverUseCase: LocationSaverUseCase): GetFullAddressUseCase {
        return GetFullAddressUseCase(locationSaverUseCase)
    }

    @Provides
    @Singleton
    fun provideLocationSaverUseCase(): LocationSaverUseCase {
        return LocationSaverUseCase()
    }

    @Provides
    @Singleton
    fun provideCheckUserModerationStatusUseCase(
        repository: ProfileRepository,
        getUserUseCase: GetUserUseCase
    ): CheckUserModerationStatusUseCase {
        return CheckUserModerationStatusUseCase(repository, getUserUseCase)
    }
    @Provides
    @Singleton
    fun provideChangeUserTypeUseCase(repository: EditProfileRepository,getUserUseCase: GetUserUseCase):ChangeUserTypeUseCase{
        return ChangeUserTypeUseCase(repository,getUserUseCase)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(repository: EditProfileRepository,getUserIdUseCase: GetUserIdUseCase):UserUpdateUseCase{
        return UserUpdateUseCase(repository,getUserIdUseCase)
    }



}
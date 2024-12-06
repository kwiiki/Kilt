package com.example.kilt.core.di

import android.content.Context
import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.data.localstorage.sharedPreference.PreferencesHelper
import com.example.kilt.domain.choosecity.usecase.GetFullAddressUseCase
import com.example.kilt.domain.choosecity.usecase.GetKatoByIdUseCase
import com.example.kilt.domain.choosecity.usecase.GetMicroDistrictByIdUseCase
import com.example.kilt.domain.choosecity.usecase.LocationSaverUseCase
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.utills.otp.SmsViewModel
import com.example.kilt.domain.config.repository.ConfigRepository
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.AddPhoneUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.DeleteSecondPhoneNumberUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UniversalUserCreateUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UserFindByOTPUseCase
import com.example.kilt.domain.editprofile.usecase.AddNewImageUseCase
import com.example.kilt.domain.editprofile.usecase.ChangeUserTypeUseCase
import com.example.kilt.domain.editprofile.usecase.DeleteImageUseCase
import com.example.kilt.domain.editprofile.usecase.GetUserPhoneNumbersUseCase
import com.example.kilt.domain.editprofile.usecase.UserUpdateUseCase
import com.example.kilt.domain.search.usecase.GetListingByIdUseCase
import com.example.kilt.domain.profile.usecase.CheckUserModerationStatusUseCase
import com.example.kilt.domain.userabout.usecase.GetUserListingsUseCase
import com.example.kilt.presentation.choosecityinedit.viewmodel.ChooseCityInEditViewModel
import com.example.kilt.presentation.editprofile.addnewimagebottomsheet.viewmodel.AddNewImageViewModel
import com.example.kilt.domain.listing.repository.ListingRepository
import com.example.kilt.domain.identification.IdentificationRepository
import com.example.kilt.domain.choosecity.repository.KatoRepository
import com.example.kilt.domain.login.repository.LoginRepository
import com.example.kilt.domain.registration.repository.RegistrationRepository
import com.example.kilt.domain.search.repository.SearchRepository
import com.example.kilt.domain.search.usecase.GetListingsByIdsUseCase
import com.example.kilt.domain.userabout.repository.UserRepository
import com.example.kilt.presentation.choosecity.viewmodel.ChooseCityViewModel
import com.example.kilt.presentation.listing.viewmodel.ListingViewModel
import com.example.kilt.presentation.login.viewModel.AuthViewModel
import com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.viewmodel.AddNewPhoneNumberViewModel
import com.example.kilt.presentation.editprofile.viewmodel.EditProfileViewModel
import com.example.kilt.presentation.profile.viewmodel.ProfileViewModel
import com.example.kilt.presentation.search.FiltersViewModel
import com.example.kilt.presentation.search.SearchResultsViewModel
import com.example.kilt.presentation.userabout.viewmodel.UserAboutViewModel
import com.example.kilt.presentation.identification.viewmodel.IdentificationViewModel
import com.example.kilt.presentation.search.viewmodel.SearchViewModel
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
        listingRepository: ListingRepository,
        configRepository: ConfigRepository
    ): ListingViewModel {
        return ListingViewModel(listingRepository, configRepository)
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
    fun provideAddNewPhoneNumberViewModel(
        getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
        addPhoneUseCase: AddPhoneUseCase,
        userFindByOTPUseCase: UserFindByOTPUseCase,
        universalUserCreateUseCase: UniversalUserCreateUseCase,
        getUserIdUseCase: GetUserIdUseCase,
        deleteSecondPhoneNumberUseCase: DeleteSecondPhoneNumberUseCase,
        getUserUseCase: GetUserUseCase
    ): AddNewPhoneNumberViewModel {
        return AddNewPhoneNumberViewModel(
            getUserPhoneNumbersUseCase,
            addPhoneUseCase,
            userFindByOTPUseCase,
            universalUserCreateUseCase,
            getUserIdUseCase,
            deleteSecondPhoneNumberUseCase,
            getUserUseCase
        )
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
    fun provideEditProfileViewModel(
        getUserUseCase: GetUserUseCase,
        getFullAddressUseCase: GetFullAddressUseCase,
        locationSaverUseCase: LocationSaverUseCase,
        changeUserTypeUseCase: ChangeUserTypeUseCase,
        userUpdateUseCase: UserUpdateUseCase
    ): EditProfileViewModel {
        return EditProfileViewModel(getUserUseCase, getFullAddressUseCase, locationSaverUseCase,changeUserTypeUseCase,userUpdateUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideChooseCityInEditViewModel(
        getKatoByIdUseCase: GetKatoByIdUseCase,
        getMicroDistrictByIdUseCase: GetMicroDistrictByIdUseCase,
        locationSaverUseCase: LocationSaverUseCase
    ): ChooseCityInEditViewModel {
        return ChooseCityInEditViewModel(
            getKatoByIdUseCase,
            getMicroDistrictByIdUseCase,
            locationSaverUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAddNewImageViewModel(
        getUserUseCase: GetUserUseCase,
        addNewImageUseCase: AddNewImageUseCase,
        deleteImageUseCase: DeleteImageUseCase
    ): AddNewImageViewModel {
        return AddNewImageViewModel(getUserUseCase, addNewImageUseCase, deleteImageUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideUserAboutViewModel(getUserListingsUseCase: GetUserListingsUseCase): UserAboutViewModel {
        return UserAboutViewModel(getUserListingsUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(checkUserModerationStatusUseCase: CheckUserModerationStatusUseCase):ProfileViewModel{
        return ProfileViewModel(checkUserModerationStatusUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideFiltersViewModel(): FiltersViewModel {
        return FiltersViewModel()
    }

    @Provides
    @ViewModelScoped
    fun provideSearchResultsViewModel(
        searchRepository: SearchRepository,
        filtersViewModel: FiltersViewModel,
        getListingByIdUseCase: GetListingByIdUseCase,
        getListingsByIdsUseCase: GetListingsByIdsUseCase
    ): SearchResultsViewModel {
        return SearchResultsViewModel(
            searchRepository = searchRepository,
            filtersStateFlow = filtersViewModel.filtersState,
            sortingFlow = filtersViewModel.sorting,
            getListingByIdUseCase = getListingByIdUseCase,
            getListingsByIdsUseCase = getListingsByIdsUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSmsViewModel(
    ): SmsViewModel {
        return SmsViewModel()
    }

}
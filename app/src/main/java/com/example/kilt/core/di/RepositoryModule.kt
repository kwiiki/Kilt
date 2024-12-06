package com.example.kilt.core.di

import com.example.kilt.core.network.ApiService
import com.example.kilt.data.search.repository.ConfigHelper
import com.example.kilt.domain.config.repository.ConfigRepository
import com.example.kilt.data.config.repository.ConfigRepositoryImpl
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepositoryImpl
import com.example.kilt.data.editprofile.repository.EditProfileRepositoryImpl
import com.example.kilt.data.favorites.repository.FavoritesRepositoryImpl
import com.example.kilt.data.profile.repostirory.ProfileRepositoryImpl
import com.example.kilt.data.userabout.repository.UserAboutRepositoryImpl
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import com.example.kilt.domain.favorites.repository.FavoritesRepository
import com.example.kilt.domain.profile.repository.ProfileRepository
import com.example.kilt.domain.userabout.repository.UserAboutRepository
import com.example.kilt.domain.listing.repository.ListingRepository
import com.example.kilt.data.listing.repository.ListingRepositoryImpl
import com.example.kilt.domain.identification.IdentificationRepository
import com.example.kilt.data.identification.IdentificationRepositoryImpl
import com.example.kilt.domain.choosecity.repository.KatoRepository
import com.example.kilt.data.choosecity.repository.KatoRepositoryImpl
import com.example.kilt.domain.login.repository.LoginRepository
import com.example.kilt.data.login.repository.LoginRepositoryImpl
import com.example.kilt.domain.registration.repository.RegistrationRepository
import com.example.kilt.data.registration.repository.RegistrationRepositoryImpl
import com.example.kilt.domain.search.repository.SearchRepository
import com.example.kilt.data.search.repository.SearchRepositoryImpl
import com.example.kilt.domain.userabout.repository.UserRepository
import com.example.kilt.data.userabout.repository.UserRepositoryImpl
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
    fun provideHomeSaleRepository(apiService: ApiService): ListingRepository {
        return ListingRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideKatoRepository(apiService: ApiService): KatoRepository {
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

    @Provides
    @Singleton
    fun provideAddNewPhoneNumberRepository(apiService: ApiService): AddNewPhoneNumberRepository {
        return AddNewPhoneNumberRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideIdentificationRepository(apiService: ApiService): IdentificationRepository {
        return IdentificationRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideEditProfileRepository(apiService: ApiService):EditProfileRepository{
        return EditProfileRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUserAboutRepository(apiService: ApiService):UserAboutRepository{
        return UserAboutRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(apiService: ApiService):ProfileRepository{
        return ProfileRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(apiService: ApiService):FavoritesRepository{
        return FavoritesRepositoryImpl(apiService)
    }
}
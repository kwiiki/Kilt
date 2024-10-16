package com.example.kilt.di

import android.content.Context
import com.example.kilt.data.dataStore.UserDataStoreManager
import com.example.kilt.data.shardePrefernce.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePreferencesHelper(@ApplicationContext context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    @Singleton
    fun provideUserDataStoreManager(@ApplicationContext context: Context): UserDataStoreManager {
        return UserDataStoreManager(context)
    }
}
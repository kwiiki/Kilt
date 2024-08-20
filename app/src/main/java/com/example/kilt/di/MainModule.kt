package com.example.kilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

//    @Provides
//    @Singleton
//    fun provideRetrofit(settings:WifiSettings):WifiManager{
//        return WifiSettings(settings)
//    }
//
//    @Provides
//    fun provideRetrofit():WifiSettings{
//        return WifiSettings()
//    }

}
package com.example.kilt.di

import com.example.kilt.repository.HomeSaleRepository
import com.example.kilt.repository.SearchRepository
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideSearchViewModel(searchRepository: SearchRepository): SearchViewModel {
        return SearchViewModel(searchRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideHomeSaleViewModel(homeSaleRepository: HomeSaleRepository): HomeSaleViewModel {
        return HomeSaleViewModel(homeSaleRepository)
    }
}
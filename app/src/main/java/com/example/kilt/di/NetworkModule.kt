
package com.example.kilt.di

import com.example.kilt.models.authentification.BioCheckOTPResult
import com.example.kilt.models.authentification.BioOtpResult
import com.example.kilt.models.authentification.CheckOtpResult
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.network.ApiService
import com.example.kilt.network.BioCheckOTPResultAdapter
import com.example.kilt.network.BioOtpResultAdapter
import com.example.kilt.network.CheckOtpResultAdapter
import com.example.kilt.network.OtpResultAdapter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://kiltapp.kz/api/v1/"
    val gson = GsonBuilder()
        .registerTypeAdapter(OtpResult::class.java, OtpResultAdapter())
        .registerTypeAdapter(BioOtpResult::class.java, BioOtpResultAdapter())
        .registerTypeAdapter(CheckOtpResult::class.java, CheckOtpResultAdapter())
        .registerTypeAdapter(BioCheckOTPResult::class.java, BioCheckOTPResultAdapter())
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging) // подключаем логирование
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
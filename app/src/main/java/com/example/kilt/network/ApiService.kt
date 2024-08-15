package com.example.kilt.network

import com.example.kilt.data.Config
import com.example.myapplication.data.HomeSale
import retrofit2.http.GET

interface ApiService {
    @GET("listings/517779")
    suspend fun getHomeSale(): HomeSale

    @GET("listings/config")
    suspend fun getConfig(): Config
}


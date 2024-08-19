package com.example.kilt.network

import com.example.kilt.data.Config
import com.example.myapplication.data.HomeSale
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("listings/520923")
    suspend fun getHomeSale(): HomeSale

    @GET("listings/config")
    suspend fun getConfig(): Config

    @POST("search")
    suspend fun search(@Body request: HomeSale): SearchResponse
}


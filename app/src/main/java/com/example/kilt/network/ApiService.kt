package com.example.kilt.network

import com.example.kilt.data.Config
import com.example.kilt.data.Count
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale
import com.example.myapplication.data.HomeSale
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("listings/{id}")
    suspend fun getHomeSale(@Path("id") id: String): HomeSale

    @GET("listings/config")
    suspend fun getConfig(): Config

    @POST("listings/search")
    suspend fun search(@Body request: THomeSale): SearchResponse

    @POST("listings/count-search")
    suspend fun getSearchCount(@Body request: THomeSale):Count
}


package com.example.kilt.network

import com.example.kilt.data.Config
import com.example.kilt.data.Count
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale
import com.example.kilt.data.kato.KatoResponse
import com.example.kilt.data.kato.MicroDistrictResponse
import com.example.kilt.data.kato.ResidentialComplexResponse
import com.example.kilt.data.HomeSale
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("listings/{id}")
    suspend fun getHomeSale(@Path("id") id: String): HomeSale

    @GET("listings/config")
    suspend fun getConfig(): Config

    @POST("listings/search")
    suspend fun search(@Body request: THomeSale): SearchResponse

    @POST("listings/count-search")
    suspend fun getSearchCount(@Body request: THomeSale):Count

    @GET("kato/parent/{id}")
    suspend fun getKato(@Path("id") id:String): KatoResponse

    @GET("kato/parent/{id}")
    suspend fun getMicroDistrict(@Path("id") id:String): MicroDistrictResponse

    @GET("residential-complex/all")
    suspend fun getResidentialComplex(@Query("starts") city: String): ResidentialComplexResponse
}


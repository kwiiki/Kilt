package com.example.kilt.network

import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.Config
import com.example.kilt.data.Count
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale
import com.example.kilt.data.kato.KatoResponse
import com.example.kilt.data.kato.MicroDistrictResponse
import com.example.kilt.data.kato.ResidentialComplexResponse
import com.example.kilt.data.HomeSale
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.OtpRequest
import com.example.kilt.data.authentification.OtpResult
import retrofit2.Response
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

    @POST("users/generate-otp-new")
    suspend fun generateOtp(@Body request: OtpRequest): OtpResult

    @POST("users/check-otp")
    suspend fun checkOtp(@Body request: CheckOtpRequest): CheckOtpResult

    @POST("users/bio-otp")
    suspend fun bioOtp(@Body request: BioOtpRequest):Response<Any>

    @POST("users/bio-otp-check")
    suspend fun bioOtpCheck(@Body request: BioOtpCheckRequest):Response<Any>

}


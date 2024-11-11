package com.example.kilt.network

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.AddPhoneDTO
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Filters
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Send
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.UserFindByOTPResult
import com.example.kilt.data.editprofile.dto.UniversalUserPhoneResult
import com.example.kilt.data.editprofile.dto.UserPhone
import com.example.kilt.data.userabout.dto.Listings
import com.example.kilt.data.userabout.dto.ListingsDTO
import com.example.kilt.models.Config
import com.example.kilt.models.Count
import com.example.kilt.models.HomeSale
import com.example.kilt.models.SearchResponse
import com.example.kilt.models.THomeSale
import com.example.kilt.models.authentification.ApiResponse
import com.example.kilt.models.authentification.BioCheckOTPResult
import com.example.kilt.models.authentification.BioOtpCheckRequest
import com.example.kilt.models.authentification.BioOtpRequest
import com.example.kilt.models.authentification.BioOtpResult
import com.example.kilt.models.authentification.CheckOtpRequest
import com.example.kilt.models.authentification.CheckOtpResult
import com.example.kilt.models.authentification.OtpRequest
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.models.authentification.UniversalUserUpdateRequest
import com.example.kilt.models.authentification.UniversalUserUpdateResult
import com.example.kilt.models.authentification.User
import com.example.kilt.models.authentification.UserFindRequest
import com.example.kilt.models.kato.KatoResponse
import com.example.kilt.models.kato.MicroDistrictResponse
import com.example.kilt.models.kato.ResidentialComplexResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun getSearchCount(@Body request: THomeSale): Count

    @GET("kato/parent/{id}")
    suspend fun getKato(@Path("id") id: String): KatoResponse

    @GET("kato/parent/{id}")
    suspend fun getMicroDistrict(@Path("id") id: String): MicroDistrictResponse

    @GET("residential-complex/all")
    suspend fun getResidentialComplex(@Query("starts") city: String): ResidentialComplexResponse

    @POST("users/generate-otp-new")
    suspend fun generateOtpNew(@Body request: OtpRequest): OtpResult

    @POST("users/generate-otp")
    suspend fun generateOtp(@Body request: OtpRequest): OtpResult

    @POST("users/check-otp")
    suspend fun checkOtp(@Body request: CheckOtpRequest): CheckOtpResult

    @POST("users/bio-otp")
    suspend fun bioOtp(@Body request: BioOtpRequest): BioOtpResult

    @POST("users/bio-otp-check")
    suspend fun bioOtpCheck(@Body request: BioOtpCheckRequest): BioCheckOTPResult

    @POST("universal/User/update")
    suspend fun universalUserUpdate(@Body request: UniversalUserUpdateRequest): UniversalUserUpdateResult

    @POST("universal/User/find")
    suspend fun universalUserFind(@Body request: UserFindRequest): User

    @GET("users/get-data/{id}")
    suspend fun getUsersData(@Path("id") id: String): User

    @Multipart
    @POST("users/upload-agency-verification-documents")
    suspend fun uploadImages(
        @Part images: List<MultipartBody.Part>
    ): ApiResponse

    @POST("users/update/{id}")
    suspend fun userUpdate(
        @Path("id") id: String,
        @Body user: User
    ): ApiResponse

    @POST("users/add-phone")
    suspend fun addPhone(
        @Body phone: Phone
    ): AddPhoneDTO

    @POST("universal/OTP/find")
    suspend fun userFindByOTP(
        @Body filters: Filters
    ): UserFindByOTPResult

    @POST("universal/UserPhone/create")
    suspend fun universalUserPhoneCreate(@Body create: Send): Response<Any>

    @POST("universal/UserPhone/find")
    suspend fun universalUserFind(@Body userPhone: UserPhone): UniversalUserPhoneResult

    @GET("listings/agent/{id}")
    suspend fun getUserListings(@Path("id") id: String):ListingsDTO

    @Multipart
    @POST("users/add-profile-image")
    suspend fun addProfileImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<Any>

    @POST("users/remove-profile-image")
    suspend fun deleteProfileImage(
        @Header("Authorization") token: String
    ): Response<Any>

    @POST("users/remove-phone")
    suspend fun removePhone(
        @Body phone: Phone,
        @Header("Authorization") token: String
    )

}


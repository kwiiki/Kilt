package com.example.kilt.domain.editprofile.repository

import com.example.kilt.data.editprofile.dto.Filters
import com.example.kilt.data.editprofile.dto.UniversalUserPhoneResult
import com.example.kilt.models.authentification.User
import okhttp3.MultipartBody
import retrofit2.Response

interface EditProfileRepository {
    suspend fun findUserPhoneNumber(filters: Filters):UniversalUserPhoneResult
    suspend fun addNewImage(image:MultipartBody.Part,token:String) : Response<Any>
    suspend fun deleteImage(token:String):Response<Any>
}
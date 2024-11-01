package com.example.kilt.repository

import com.example.kilt.data.authentification.User
import com.example.kilt.data.authentification.UserUpdateResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface IdentificationRepository {

    suspend fun identifyUser(id:String,user: User):UserUpdateResult
    suspend fun uploadImages(images: List<MultipartBody.Part>): UserUpdateResult
    suspend fun checkIdentifiedStatus(id:String):Int
}


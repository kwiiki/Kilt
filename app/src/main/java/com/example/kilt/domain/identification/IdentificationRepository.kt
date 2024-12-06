package com.example.kilt.domain.identification

import com.example.kilt.domain.editprofile.model.UpdatedUser
import com.example.kilt.models.authentification.UserUpdateResult
import okhttp3.MultipartBody

interface IdentificationRepository {

    suspend fun identifyUser(id:String,user: UpdatedUser):UserUpdateResult
    suspend fun uploadImages(images: List<MultipartBody.Part>): UserUpdateResult
    suspend fun checkIdentifiedStatus(id:String):Int
}


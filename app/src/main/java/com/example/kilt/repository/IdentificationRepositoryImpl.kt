package com.example.kilt.repository

import com.example.kilt.data.authentification.User
import com.example.kilt.data.authentification.UserUpdateResult
import com.example.kilt.network.ApiService
import okhttp3.MultipartBody

class IdentificationRepositoryImpl(private val apiService: ApiService):IdentificationRepository {
    override suspend fun identifyUser(id: String, user: User): UserUpdateResult {
        val response = apiService.userUpdate(id = id, user = user)
        return if (response.success && response.user != null) {
            UserUpdateResult.UpdateUserSuccess(success = true, user = response.user)
        } else {
            UserUpdateResult.UpdateUserFailure(success = false, message = "Update failed")
        }
    }

    override suspend fun uploadImages(images: List<MultipartBody.Part>): UserUpdateResult {
        val response = apiService.uploadImages(images = images)
        return if (response.success && response.user != null) {
            UserUpdateResult.UpdateUserSuccess(success = true, user = response.user)
        } else {
            UserUpdateResult.UpdateUserFailure(success = false, message = "Upload failed")
        }
    }

}


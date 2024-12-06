package com.example.kilt.data.identification

import com.example.kilt.domain.editprofile.model.UpdatedUser
import com.example.kilt.domain.identification.IdentificationRepository
import com.example.kilt.models.authentification.UserUpdateResult
import com.example.kilt.core.network.ApiService
import okhttp3.MultipartBody

class IdentificationRepositoryImpl(private val apiService: ApiService): IdentificationRepository {
    override suspend fun identifyUser(id: String, user: UpdatedUser): UserUpdateResult {
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
    override suspend fun checkIdentifiedStatus(id: String): Int {
        return try {
            val userData = apiService.getUsersData(id)
            userData.agency_verification_status
        } catch (e: Exception) {
            0
        }
    }
}


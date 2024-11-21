package com.example.kilt.data.editprofile.repository

import com.example.kilt.data.editprofile.dto.Filters
import com.example.kilt.data.editprofile.dto.UniversalUserPhoneResult
import com.example.kilt.data.editprofile.dto.UserPhone
import com.example.kilt.domain.editprofile.model.UpdatedUser
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import com.example.kilt.network.ApiService
import okhttp3.MultipartBody
import retrofit2.Response

class EditProfileRepositoryImpl(private val apiService: ApiService):EditProfileRepository {
    override suspend fun findUserPhoneNumber(filters: Filters):UniversalUserPhoneResult {
        return apiService.universalUserFind(UserPhone(filters))
    }

    override suspend fun addNewImage(image: MultipartBody.Part,token:String): Response<Any> {
       return  apiService.addProfileImage(image = image, token = token)
    }

    override suspend fun updateUser(id: String, user: UpdatedUser){
        apiService.userUpdate(id = id, user = user)
    }

    override suspend fun deleteImage(token: String): Response<Any> {
        return apiService.deleteProfileImage(token)
    }

    override suspend fun changeUserType(token: String) {
        apiService.changeUserType("Bearer $token")
    }
}
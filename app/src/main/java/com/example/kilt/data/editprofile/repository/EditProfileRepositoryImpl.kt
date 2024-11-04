package com.example.kilt.data.editprofile.repository

import com.example.kilt.data.editprofile.dto.AddPhoneDTO
import com.example.kilt.domain.editprofile.model.Phone
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import com.example.kilt.network.ApiService

class EditProfileRepositoryImpl(private val apiService: ApiService): EditProfileRepository {
    override suspend fun addPhone(phone: Phone): Result<AddPhoneDTO> {
        return try {
            val response = apiService.addPhone(phone)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
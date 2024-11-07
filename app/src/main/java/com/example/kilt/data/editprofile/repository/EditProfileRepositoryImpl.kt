package com.example.kilt.data.editprofile.repository

import com.example.kilt.data.editprofile.dto.Filters
import com.example.kilt.data.editprofile.dto.UniversalUserPhoneResult
import com.example.kilt.data.editprofile.dto.UserPhone
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import com.example.kilt.network.ApiService

class EditProfileRepositoryImpl(private val apiService: ApiService):EditProfileRepository {
    override suspend fun findUserPhoneNumber(filters: Filters):UniversalUserPhoneResult {
        return apiService.universalUserFind(UserPhone(filters))
    }
}
package com.example.kilt.data.profile.repostirory

import com.example.kilt.domain.profile.model.Result
import com.example.kilt.domain.profile.repository.ProfileRepository
import com.example.kilt.network.ApiService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val apiService: ApiService):ProfileRepository {
    override suspend fun checkUserModerationStatus(token: String): Result {
        return apiService.getUserModerationStatus("Bearer $token")
    }
}
package com.example.kilt.data.userabout.repository

import android.util.Log
import com.example.kilt.domain.userabout.repository.UserRepository
import com.example.kilt.models.authentification.User
import com.example.kilt.core.network.ApiService

class UserRepositoryImpl(private val apiService: ApiService): UserRepository {
    override suspend fun getUserData(id: String): User {
        Log.d("checkCounter", "getUserData: Count")
        Log.d("checkCounter", id)
        return apiService.getUsersData(id = id)
    }
}
package com.example.kilt.repository

import android.util.Log
import com.example.kilt.models.authentification.User
import com.example.kilt.network.ApiService

class UserRepositoryImpl(private val apiService: ApiService):UserRepository {
    override suspend fun getUserData(id: String): User {
        Log.d("checkCounter", "getUserData: Count")
        return apiService.getUsersData(id = id)
    }
}
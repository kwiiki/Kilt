package com.example.kilt.repository

import com.example.kilt.models.authentification.User
import com.example.kilt.network.ApiService

class UserRepositoryImpl(private val apiService: ApiService):UserRepository {
    override suspend fun getUserData(id: String): User {
        return apiService.getUsersData(id = id)
    }
}
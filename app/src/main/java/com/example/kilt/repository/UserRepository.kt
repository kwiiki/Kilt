package com.example.kilt.repository

import com.example.kilt.models.authentification.User

interface UserRepository {
    suspend fun getUserData(id:String):User
}
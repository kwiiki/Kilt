package com.example.kilt.domain.profile.repository

import com.example.kilt.domain.profile.model.Result

interface ProfileRepository {
    suspend fun checkUserModerationStatus(token:String):Result

}
package com.example.kilt.domain.editprofile.repository

import com.example.kilt.data.editprofile.dto.Filters
import com.example.kilt.data.editprofile.dto.UniversalUserPhoneResult

interface EditProfileRepository {
    suspend fun findUserPhoneNumber(filters: Filters):UniversalUserPhoneResult
}
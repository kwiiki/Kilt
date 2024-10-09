package com.example.kilt.repository

import com.example.kilt.data.OtpResult

interface LoginRepository {
    suspend fun generateOtp(phone: String): OtpResult
    suspend fun checkOtp(phone: String): Result<Any>
    suspend fun sendOtp(phoneNumber: String): Result<Unit>
}
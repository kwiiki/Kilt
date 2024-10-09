package com.example.kilt.repository

import android.util.Log
import com.example.kilt.data.Otp
import com.example.kilt.data.OtpRequest
import com.example.kilt.data.OtpResult
import com.example.kilt.network.ApiService

class LoginRepositoryImpl(private val apiService: ApiService,):LoginRepository {
    override suspend fun generateOtp(phone: String): OtpResult {
            return apiService.generateOtp(OtpRequest(Otp(phone)))
    }
    override suspend fun checkOtp(phone: String): Result<Any> {
        return try {
            Log.d("checkOtp", "checkOtp: $phone")
            val response = apiService.checkOtp(OtpRequest(Otp(phone)))
            if (response.isSuccessful) {
                Result.success(response.body() ?: "Success")
            } else {
                Result.failure(Throwable(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(phoneNumber: String): Result<Unit> {
        return try {
//            apiService.generateOtp(phoneNumber)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
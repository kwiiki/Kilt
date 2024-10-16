package com.example.kilt.repository

import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.Otp
import com.example.kilt.data.authentification.OtpRequest
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.network.ApiService

class LoginRepositoryImpl(private val apiService: ApiService):LoginRepository {
    override suspend fun generateOtp(phone: String): OtpResult {
            return apiService.generateOtpNew(OtpRequest(Otp(phone)))
    }
    override suspend fun checkOtp(checkOtpRequest: CheckOtpRequest): CheckOtpResult {
        return apiService.checkOtp(checkOtpRequest)
    }
}
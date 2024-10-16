package com.example.kilt.repository

import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.OtpResult

interface LoginRepository {
    suspend fun generateOtp(phone: String): OtpResult
    suspend fun checkOtp(checkOtpRequest: CheckOtpRequest): CheckOtpResult

}
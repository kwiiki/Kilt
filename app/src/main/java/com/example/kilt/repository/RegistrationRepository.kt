package com.example.kilt.repository

import com.example.kilt.data.authentification.BioCheckOTPResult
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.BioOtpResult
import retrofit2.Response

interface RegistrationRepository {
    suspend fun bioOtp(bioOtpRequest: BioOtpRequest): BioOtpResult
    suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): BioCheckOTPResult
}
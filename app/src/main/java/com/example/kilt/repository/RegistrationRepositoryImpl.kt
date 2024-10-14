package com.example.kilt.repository

import com.example.kilt.data.authentification.BioCheckOTPResult
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.BioOtpResult
import com.example.kilt.network.ApiService
import retrofit2.Response

class RegistrationRepositoryImpl(private val apiService: ApiService):RegistrationRepository {
    override suspend fun bioOtp(bioOtpRequest: BioOtpRequest): BioOtpResult {
        return apiService.bioOtp(bioOtpRequest)
    }

    override suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): BioCheckOTPResult {
        return apiService.bioOtpCheck(bioOtpCheckRequest)
    }
}
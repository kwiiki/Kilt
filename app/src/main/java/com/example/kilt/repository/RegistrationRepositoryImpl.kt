package com.example.kilt.repository

import com.example.kilt.data.authentification.BioCheckOTPResult
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.BioOtpResult
import com.example.kilt.data.authentification.Filters
import com.example.kilt.data.authentification.Otp
import com.example.kilt.data.authentification.OtpRequest
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.data.authentification.UniversalUserUpdateRequest
import com.example.kilt.data.authentification.UniversalUserUpdateResult
import com.example.kilt.data.authentification.Update
import com.example.kilt.network.ApiService

class RegistrationRepositoryImpl(private val apiService: ApiService) : RegistrationRepository {
    override suspend fun bioOtp(bioOtpRequest: BioOtpRequest): BioOtpResult {
        return apiService.bioOtp(bioOtpRequest)
    }

    override suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): BioCheckOTPResult {
        return apiService.bioOtpCheck(bioOtpCheckRequest)
    }

    override suspend fun generateOTP(phone: String): OtpResult {
        return apiService.generateOtp(OtpRequest(Otp(phone)))
    }

    override suspend fun universalUserUpdate(
        userId: Int,
        userType: String
    ): UniversalUserUpdateResult {
        return apiService.universalUserUpdate(UniversalUserUpdateRequest(Filters(userId), Update(userType)))
    }


}
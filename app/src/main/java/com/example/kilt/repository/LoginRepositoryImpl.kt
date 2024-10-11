package com.example.kilt.repository

import android.util.Log
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.Otp
import com.example.kilt.data.authentification.OtpRequest
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.network.ApiService
import retrofit2.Response

class LoginRepositoryImpl(private val apiService: ApiService,):LoginRepository {
    override suspend fun generateOtp(phone: String): OtpResult {
            return apiService.generateOtp(OtpRequest(Otp(phone)))
    }
    override suspend fun checkOtp(checkOtpRequest: CheckOtpRequest): CheckOtpResult {
        return apiService.checkOtp(checkOtpRequest)
    }

    override suspend fun bioOtp(bioOtpRequest: BioOtpRequest): Response<Any> {
        return apiService.bioOtp(bioOtpRequest)
    }

    override suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): Response<Any> {
        return apiService.bioOtpCheck(bioOtpCheckRequest)
    }

}
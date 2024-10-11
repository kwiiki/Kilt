package com.example.kilt.repository

import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.OtpResult
import retrofit2.Response

interface LoginRepository {
    suspend fun generateOtp(phone: String): OtpResult
    suspend fun checkOtp(checkOtpRequest: CheckOtpRequest): CheckOtpResult
    suspend fun bioOtp(bioOtpRequest: BioOtpRequest):Response<Any>
    suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest):Response<Any>
}
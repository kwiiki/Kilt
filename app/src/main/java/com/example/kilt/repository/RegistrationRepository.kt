package com.example.kilt.repository

import com.example.kilt.data.authentification.BioCheckOTPResult
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.BioOtpResult
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.data.authentification.UniversalUserUpdateResult

interface RegistrationRepository {
    suspend fun bioOtp(bioOtpRequest: BioOtpRequest): BioOtpResult
    suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): BioCheckOTPResult
    suspend fun generateOTP(phone:String):OtpResult
    suspend fun universalUserUpdate(userId:Int,userType:String): UniversalUserUpdateResult
}
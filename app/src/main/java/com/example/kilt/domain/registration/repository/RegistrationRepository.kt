package com.example.kilt.domain.registration.repository

import com.example.kilt.models.authentification.BioCheckOTPResult
import com.example.kilt.models.authentification.BioOtpCheckRequest
import com.example.kilt.models.authentification.BioOtpRequest
import com.example.kilt.models.authentification.BioOtpResult
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.models.authentification.UniversalUserUpdateResult

interface RegistrationRepository {
    suspend fun bioOtp(bioOtpRequest: BioOtpRequest): BioOtpResult
    suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): BioCheckOTPResult
    suspend fun handleBioOtp(bioOtpRequest: BioOtpRequest):BioOtpResult
    suspend fun generateOTP(phone:String):OtpResult
    suspend fun handleOtpGeneration(phoneNumber: String): OtpResult
    suspend fun universalUserUpdate(userId:Int,userType:String): UniversalUserUpdateResult
}
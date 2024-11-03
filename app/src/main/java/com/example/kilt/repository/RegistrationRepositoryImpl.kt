package com.example.kilt.repository

import android.util.Log
import com.example.kilt.models.authentification.BioCheckOTPResult
import com.example.kilt.models.authentification.BioOtpCheckRequest
import com.example.kilt.models.authentification.BioOtpRequest
import com.example.kilt.models.authentification.BioOtpResult
import com.example.kilt.models.authentification.ErrorResponse
import com.example.kilt.models.authentification.Filters
import com.example.kilt.models.authentification.Otp
import com.example.kilt.models.authentification.OtpRequest
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.models.authentification.UniversalUserUpdateRequest
import com.example.kilt.models.authentification.UniversalUserUpdateResult
import com.example.kilt.models.authentification.Update
import com.example.kilt.network.ApiService

class RegistrationRepositoryImpl(private val apiService: ApiService) : RegistrationRepository {
    override suspend fun bioOtp(bioOtpRequest: BioOtpRequest): BioOtpResult {
        return apiService.bioOtp(bioOtpRequest)
    }

    override suspend fun bioOtpCheck(bioOtpCheckRequest: BioOtpCheckRequest): BioCheckOTPResult {
        return apiService.bioOtpCheck(bioOtpCheckRequest)
    }

    override suspend fun handleBioOtp(
       bioOtpRequest: BioOtpRequest
    ):BioOtpResult {
        return try {
            Log.d("bio otp", "handleBioOtp: ${bioOtpRequest}")
            when (val result = bioOtp(bioOtpRequest = bioOtpRequest)) {
                is BioOtpResult.Success -> result
                is BioOtpResult.RegisteredUser -> result
                is BioOtpResult.Failure -> BioOtpResult.Failure(message = "Номер телефона введён неверно, либо вы не зарегистрированы. Пожалуйста, зарегистрируйтесь", success = false)
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Error generating OTP", e)
            BioOtpResult.Failure(message = "Номер телефона введён неверно, либо вы не зарегистрированы. Пожалуйста, зарегистрируйтесь", success = false)
        }
    }

    override suspend fun generateOTP(phone: String): OtpResult {
        return apiService.generateOtp(OtpRequest(Otp(phone)))
    }

    override suspend fun handleOtpGeneration(phoneNumber: String): OtpResult {
        Log.d("phoneNumber", "handleOtpGeneration: $phoneNumber")
        return try {
            when (val result = generateOTP(phoneNumber)) {
                is OtpResult.Success -> result
                is OtpResult.Failure -> OtpResult.Failure(ErrorResponse("Номер телефона введён неверно, либо вы не зарегистрированы. Пожалуйста, зарегистрируйтесь"))
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Error generating OTP", e)
            OtpResult.Failure(ErrorResponse("Номер телефона введён неверно, либо вы не зарегистрированы. Пожалуйста, зарегистрируйтесь"))
        }
    }

    override suspend fun universalUserUpdate(
        userId: Int,
        userType: String
    ): UniversalUserUpdateResult {
        return apiService.universalUserUpdate(UniversalUserUpdateRequest(Filters(userId), Update(userType)))
    }


}
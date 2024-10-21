package com.example.kilt.repository

import android.util.Log
import com.example.kilt.data.authentification.CheckOtp
import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.ErrorResponse
import com.example.kilt.data.authentification.Filters
import com.example.kilt.data.authentification.Otp
import com.example.kilt.data.authentification.OtpRequest
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.data.authentification.UniversalUserUpdateRequest
import com.example.kilt.data.authentification.UniversalUserUpdateResult
import com.example.kilt.data.authentification.Update
import com.example.kilt.network.ApiService

class LoginRepositoryImpl(private val apiService: ApiService):LoginRepository {

    override suspend fun generateOtp(phone: String): OtpResult {
        return apiService.generateOtpNew(OtpRequest(Otp(phone)))
    }
    override suspend fun handleOtpGeneration(phoneNumber: String): OtpResult {
        return try {
            when (val result = generateOtp(phoneNumber)) {
                is OtpResult.Success -> result
                is OtpResult.Failure -> OtpResult.Failure(ErrorResponse("Номер телефона введён неверно, либо вы не зарегистрированы. Пожалуйста, зарегистрируйтесь"))
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Error generating OTP", e)
            OtpResult.Failure(ErrorResponse("Номер телефона введён неверно, либо вы не зарегистрированы. Пожалуйста, зарегистрируйтесь"))
        }
    }
    override suspend fun checkOtp(checkOtpRequest: CheckOtpRequest): CheckOtpResult {
        return apiService.checkOtp(checkOtpRequest)
    }
    override suspend fun handleCheckOtp(phoneNumber: String, otpCode: String, firebaseToken: String, userType: String): CheckOtpResult {
        try {
            val checkOtpRequest = CheckOtpRequest(
                otp = CheckOtp(phone = phoneNumber, code = otpCode),
                fcmToken = firebaseToken,
                referal = ""
            )

            val result = checkOtp(checkOtpRequest)
            return when (result) {
                is CheckOtpResult.Success -> handleSuccessfulOtp(result, userType)
                is CheckOtpResult.Failure -> CheckOtpResult.Failure(ErrorResponse(result.error.msg))
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Failed to check OTP", e)
            return CheckOtpResult.Failure(ErrorResponse("Не удалось проверить код"))
        }
    }

    private suspend fun handleSuccessfulOtp(result: CheckOtpResult.Success, userType: String): CheckOtpResult {
        val userId = result.user.id
        return try {
            val updateResult = universalUserUpdate(userId = userId, userType = userType)
            Log.d("universalUserUpdate", "User update result: $updateResult")
            if (updateResult.success) {
                result
            } else {
                CheckOtpResult.Failure(ErrorResponse("Ошибка обновления пользователя"))
            }
        } catch (e: Exception) {
            Log.e("universalUserUpdate", "User update failed", e)
            CheckOtpResult.Failure(ErrorResponse("Ошибка обновления пользователя"))
        }
    }
    private suspend fun universalUserUpdate(
        userId: Int,
        userType: String
    ): UniversalUserUpdateResult {
        return apiService.universalUserUpdate(UniversalUserUpdateRequest(Filters(userId), Update(userType)))
    }
}
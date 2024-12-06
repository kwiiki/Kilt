package com.example.kilt.data.login.repository

import android.util.Log
import com.example.kilt.domain.login.repository.LoginRepository
import com.example.kilt.models.authentification.CheckOtp
import com.example.kilt.models.authentification.CheckOtpRequest
import com.example.kilt.models.authentification.CheckOtpResult
import com.example.kilt.models.authentification.ErrorResponse
import com.example.kilt.models.authentification.Otp
import com.example.kilt.models.authentification.OtpRequest
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.core.network.ApiService

class LoginRepositoryImpl(private val apiService: ApiService): LoginRepository {

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

    override suspend fun handleCheckOtp(
        phoneNumber: String,
        otpCode: String,
        firebaseToken: String,
        userType: String
    ): CheckOtpResult {
        try {
            val checkOtpRequest = CheckOtpRequest(
                otp = CheckOtp(phone = phoneNumber, code = otpCode),
                fcmToken = firebaseToken,
                referal = ""
            )
            return when (val result = checkOtp(checkOtpRequest)) {
                is CheckOtpResult.Success -> result
                is CheckOtpResult.Failure -> CheckOtpResult.Failure(ErrorResponse(result.error.msg))
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Failed to check OTP", e)
            return CheckOtpResult.Failure(ErrorResponse("Не удалось проверить код"))
        }
    }
}
//    private suspend fun handleSuccessfulOtp(result: CheckOtpResult.Success, userType: String): CheckOtpResult {
//        val userId = result.user.id
//        return try {
//            val updateResult = universalUserUpdate(userId = userId, userType = userType)
//            Log.d("universalUserUpdate", "User update result: $updateResult")
//            if (updateResult.success) {
//                result
//            } else {
//                CheckOtpResult.Failure(ErrorResponse("Ошибка обновления"))
//            }
//        } catch (e: Exception) {
//            Log.e("universalUserUpdate", "User update failed", e)
//            CheckOtpResult.Failure(ErrorResponse("Ошибка обновления "))
//        }
//    }
//    private suspend fun universalUserUpdate(
//        userId: Int,
//        userType: String
//    ): UniversalUserUpdateResult {
//        return apiService.universalUserUpdate(UniversalUserUpdateRequest(Filters(userId), Update(userType)))
//    }

package com.example.kilt.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.authentification.CheckOtp
import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.ErrorResponse
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.repository.LoginRepository
import com.example.kilt.screens.profile.LoginUiState
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,private val application: Application) :
    AndroidViewModel(application) {
    private val _otpResult = mutableStateOf<OtpResult?>(null)
    val otpResult: State<OtpResult?> = _otpResult

    private val _checkOtpResult = mutableStateOf<CheckOtpResult?>(null)
    val checkOtpResult:State<CheckOtpResult?> = _checkOtpResult

    private val _loginUiState = mutableStateOf(LoginUiState())
    val loginUiState: State<LoginUiState> = _loginUiState

    fun sendPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            try {
                val result = loginRepository.generateOtp(phoneNumber)
                Log.d("sendPhoneNumber", "sendPhoneNumber: $result")
                _otpResult.value = when (result) {
                    is OtpResult.Success -> result
                    is OtpResult.Failure -> OtpResult.Failure(ErrorResponse(result.error.msg))
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error generating OTP", e)
                _otpResult.value = OtpResult.Failure(ErrorResponse("Номер телефона введён неверно,либо вы не зарегистрированы.Пожалуйсто, зарегистрируйтесь"))
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun checkOtp() {
        viewModelScope.launch {
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseToken = task.result
                        val phoneNumber = "+7${_loginUiState.value.phone}"
                        val otpCode = _loginUiState.value.code

                            Log.d("checkOtp", "checkOtp: $firebaseToken")
                        Log.d("checkOtp", "checkOtp: $phoneNumber")
                        Log.d("checkOtp", "checkOtp: $otpCode")
                        // Создаем объект запроса
                        val checkOtpRequest = CheckOtpRequest(
                            checkOtp = CheckOtp(phone = phoneNumber, code = otpCode.trim()),
                            fcmToken = firebaseToken,
                            referal = """"""
                        )
                        // Отправляем запрос через репозиторий
                        viewModelScope.launch {
                            val result = loginRepository.checkOtp(checkOtpRequest)
                            Log.d("checkOtp", "checkOtp: $result")
                            _checkOtpResult.value = when (result) {
                                is CheckOtpResult.Success -> result
                                is CheckOtpResult.Failure -> CheckOtpResult.Failure(ErrorResponse(result.error.msg))
                            }

                        }
                    } else {
                        Log.e("FirebaseToken", "Ошибка получения токена Firebase")
                        _otpResult.value = OtpResult.Failure(ErrorResponse("Введите корректный код "))
                    }
                }
            } catch (e: Exception) {
                _otpResult.value = OtpResult.Failure(ErrorResponse("Не удалось отправить код"))
            }
        }
    }

    fun updateCode(newCode: String) {
        _loginUiState.value = _loginUiState.value.copy(code = newCode)

        // Если код состоит из 4 символов, отправляем запрос
        if (newCode.length == 4) {
            checkOtp()
        }
    }

//    fun startSmsCodeRetrieval() {
//        val client = SmsRetriever.getClient(application)
//        val task = client.startSmsRetriever()
//        task.addOnSuccessListener {
//        }
//        task.addOnFailureListener {
//            // Failed to start SMS retriever
//        }
//    }
//
//    internal val smsVerificationReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
//                val extras = intent.extras
//                val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
//                when (status?.statusCode) {
//                    CommonStatusCodes.SUCCESS -> {
//                        val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
//                        if (message != null) {
//                            val code = extractCodeFromMessage(message)
//                            updateCode(code)
//                        }
//                    }
//                    CommonStatusCodes.TIMEOUT -> {
//                        // SMS retrieval timed out
//                    }
//                }
//            }
//        }
//    }
//    private fun extractCodeFromMessage(message: String): String {
//        Log.d("message", "extractCodeFromMessage: $message")
//        val regex = "Код подтверждения в Kilt - (\\d{4})".toRegex()
//        val matchResult = regex.find(message)
//        return matchResult?.groupValues?.get(1) ?: ""
//    }
}
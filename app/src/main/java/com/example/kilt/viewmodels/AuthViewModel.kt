package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.authentification.BioCheckOTPResult
import com.example.kilt.data.authentification.BioOtpCheckRequest
import com.example.kilt.data.authentification.BioOtpRequest
import com.example.kilt.data.authentification.BioOtpResult
import com.example.kilt.data.authentification.CheckOtp
import com.example.kilt.data.authentification.CheckOtpRequest
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.DeviceInfo
import com.example.kilt.data.authentification.ErrorResponse
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.enums.UserType
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.RegistrationRepository
import com.example.kilt.screens.profile.registration.RegistrationUiState
import com.google.android.datatransport.BuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val loginRepository: LoginRepository,
   ):ViewModel() {

    private val _registrationUiState = mutableStateOf(RegistrationUiState())
    val registrationUiState:State<RegistrationUiState> = _registrationUiState

    private val _bioOtpResult = mutableStateOf<BioOtpResult?>(null)
    val bioOtpResult: State<BioOtpResult?> = _bioOtpResult

    private val _bioCheckOTPResult = mutableStateOf<BioCheckOTPResult?>(null)
    val bioCheckOTPResult:State<BioCheckOTPResult?> = _bioCheckOTPResult

    private val _otpResult = mutableStateOf<OtpResult?>(null)
    val otpResult: State<OtpResult?> = _otpResult

    private val _checkOtpResult = mutableStateOf<CheckOtpResult?>(null)
    val checkOtpResult: State<CheckOtpResult?> = _checkOtpResult

    fun sendPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            try {
                val result = loginRepository.generateOtp(phoneNumber)
                Log.d("sendPhoneNumber", "sendPhoneNumber: $result")
                _otpResult.value = when (result) {
                    is OtpResult.Success -> result
                    is OtpResult.Failure -> OtpResult.Failure(ErrorResponse("Номер телефона введён неверно,либо вы не зарегистрированы.Пожалуйсто, зарегистрируйтесь"))
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error generating OTP", e)
                _otpResult.value =
                    OtpResult.Failure(ErrorResponse("Номер телефона введён неверно,либо вы не зарегистрированы.Пожалуйсто, зарегистрируйтесь"))
            }
        }
    }
    private fun checkOtp() {
        viewModelScope.launch {
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseToken = task.result
                        val phoneNumber = "+7${_registrationUiState.value.phone}"
                        val otpCode = _registrationUiState.value.code

                        Log.d("checkOtp", "checkOtp: $firebaseToken")
                        Log.d("checkOtp", "checkOtp: $phoneNumber")
                        Log.d("checkOtp", "checkOtp: $otpCode")
                        // Создаем объект запроса
                        val checkOtpRequest = CheckOtpRequest(
                            otp = CheckOtp(phone = phoneNumber, code = otpCode.trim()),
                            fcmToken = firebaseToken,
                            referal = """"""
                        )
                        // Отправляем запрос через репозиторий
                        viewModelScope.launch {
                            val result = loginRepository.checkOtp(checkOtpRequest)
                            delay(3000)
                            _checkOtpResult.value = when (result) {
                                is CheckOtpResult.Success -> result
                                is CheckOtpResult.Failure -> CheckOtpResult.Failure(
                                    ErrorResponse(
                                        result.error.msg
                                    )
                                )
                            }

                        }
                    } else {
                        Log.e("FirebaseToken", "Ошибка получения токена Firebase")
                        _otpResult.value =
                            OtpResult.Failure(ErrorResponse("Введите корректный код "))
                    }
                }
            } catch (e: Exception) {
                _otpResult.value = OtpResult.Failure(ErrorResponse("Не удалось отправить код"))
            }
        }
    }
    fun bioOtp() {
        viewModelScope.launch {
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseToken = task.result
                        val phoneNumber = registrationUiState.value.phone
                        val userType = registrationUiState.value.userType
                        val iin = registrationUiState.value.iin

                        Log.d("bioOtp", "App Version: ${BuildConfig.VERSION_NAME}")
                        Log.d("bioOtp", "Firebase Token: $firebaseToken")
                        Log.d("bioOtp", "Phone Number: $phoneNumber")
                        Log.d("bioOtp", "User Type: ${userType.value}")
                        Log.d("bioOtp", "IIN: $iin")
                        val bioOtpRequest = BioOtpRequest(
                            fcmToken = firebaseToken,
                            deviceInfo = DeviceInfo(appVersion = "1.0"),
                            phone = "7$phoneNumber",
                            iin = iin,
                            userType = userType.value
                        )
                        viewModelScope.launch {
                            try {
                                val response = registrationRepository.bioOtp(bioOtpRequest)
                                Log.d("bioOtp", "bioOtp: $response")
                                _bioOtpResult.value = response
                            } catch (e: Exception) {
                                Log.e("bioOtp", "Ошибка во время запроса: ${e.message}")
                                _bioOtpResult.value = BioOtpResult.Failure(
                                    success = false,
                                    message = "Ошибка во время выполнения запроса"
                                )
                            }
                        }
                    } else {
                        Log.e("FirebaseToken", "Ошибка получения токена Firebase")
                    }
                }
            } catch (e: Exception) {
                Log.e("bioOtp", "Ошибка: ${e.message}")
            }
        }
    }
    private fun bioOtpCheck(){
        viewModelScope.launch {
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseToken = task.result
                        val phoneNumber = "7${registrationUiState.value.phone}"
                        val otpCode = registrationUiState.value.code
                        val iin = registrationUiState.value.iin

                        Log.d("bioOTP", "bioOTP: $firebaseToken")
                        Log.d("bioOTP", "bioOTP: $phoneNumber")
                        Log.d("bioOTP", "bioOTP: $otpCode")
                        // Создаем объект запроса
                        val bioOtpCheckRequest = BioOtpCheckRequest(
                            fcmToken = firebaseToken,
                            referal = """""",
                            phone = phoneNumber,
                            iin = iin,
                            code = otpCode
                        )
                        // Отправляем запрос через репозиторий
                        viewModelScope.launch {
                            Log.d("bioOTP", "bioOTP: sec")
                            val result = registrationRepository.bioOtpCheck(bioOtpCheckRequest)
                            delay(3000)
                            Log.d("bioOTP", "bioOTP: $result")
                            _bioCheckOTPResult.value = when (result) {
                                is BioCheckOTPResult.Success -> result
                                is BioCheckOTPResult.Failure -> BioCheckOTPResult.Failure(ErrorResponse(result.error.msg))
                            }
                        }
                    } else {
                        Log.e("FirebaseToken", "Ошибка получения токена Firebase")
                        _bioCheckOTPResult.value = BioCheckOTPResult.Failure(ErrorResponse("Введите корректный код "))
                    }
                }
            } catch (e: Exception) {
                _bioCheckOTPResult.value = BioCheckOTPResult.Failure(ErrorResponse("Не удалось отправить код"))
            }
        }
    }
    fun updateForSixCode(newCode: String) {
        _registrationUiState.value = registrationUiState.value.copy(code = newCode)
        if (newCode.length == 6) {
            bioOtpCheck()
        }
    }
    fun updateForFourCode(newCode: String) {
        _registrationUiState.value = registrationUiState.value.copy(code = newCode)
        if (newCode.length == 4) {
            checkOtp()
        }
    }
    fun updatePhone(phone: String) {
        _registrationUiState.value = registrationUiState.value.copy(phone = phone)
    }
    fun updateIin(iin: String) {
        _registrationUiState.value = registrationUiState.value.copy(iin = iin)
    }
    fun updateUserType(userType: UserType) {
        _registrationUiState.value = registrationUiState.value.copy(userType = userType)
    }
    fun clear(){
        _registrationUiState.value.phone = ""
    }
}
package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.kilt.data.authentification.UserWithMetadata
import com.example.kilt.data.dataStore.UserDataStoreManager
import com.example.kilt.data.shardePrefernce.PreferencesHelper
import com.example.kilt.enums.UserType
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.RegistrationRepository
import com.example.kilt.screens.profile.registration.RegistrationUiState
import com.google.android.datatransport.BuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val loginRepository: LoginRepository,
    private val userDataStoreManager: UserDataStoreManager,
    private val preferencesHelper: PreferencesHelper
   ):ViewModel() {
    val user: Flow<UserWithMetadata?> = userDataStoreManager.userDataFlow
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

    private val _isUserAuthenticated = mutableStateOf(preferencesHelper.isUserAuthenticated())
    val isUserAuthenticated: State<Boolean> = _isUserAuthenticated

    private val _isUserIdentified = mutableStateOf(preferencesHelper.isUserIdentified())
    val isUserIdentified: State<Boolean> = _isUserIdentified

    private val _timerCount = mutableIntStateOf(59)
    val timerCount: State<Int> = _timerCount

    private var timerJob: Job? = null

    fun sendPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            try {
                val result = loginRepository.generateOtp(phoneNumber)
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
    fun generateOTP(phoneNumber: String){
        viewModelScope.launch {
            try {
                Log.d("wd", "sendPhoneNumber: $phoneNumber")
                val result = registrationRepository.generateOTP(phoneNumber)
                Log.d("wd", "sendPhoneNumber: $result")
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
                val firebaseToken = getFirebaseToken()
                val phoneNumber = "+7${_registrationUiState.value.phone}"
                val otpCode = _registrationUiState.value.code.trim()

                Log.d("checkOtp", "firebaseToken: $firebaseToken")
                Log.d("checkOtp", "phoneNumber: $phoneNumber")
                Log.d("checkOtp", "otpCode: $otpCode")

                val checkOtpRequest = CheckOtpRequest(
                    otp = CheckOtp(phone = phoneNumber, code = otpCode),
                    fcmToken = firebaseToken,
                    referal = ""
                )

                val result = loginRepository.checkOtp(checkOtpRequest)
                _checkOtpResult.value = when (result) {
                    is CheckOtpResult.Success -> handleSuccessfulOtp(result)
                    is CheckOtpResult.Failure -> CheckOtpResult.Failure(ErrorResponse(result.error.msg))
                }
            } catch (e: Exception) {
                Log.e("checkOtp", "Failed to check OTP", e)
                _checkOtpResult.value = CheckOtpResult.Failure(ErrorResponse("Не удалось проверить код"))
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
    private fun bioOtpCheck() {
        viewModelScope.launch {
            try {
                val firebaseToken = getFirebaseToken()
                val phoneNumber = "7${registrationUiState.value.phone}"
                val otpCode = registrationUiState.value.code
                val iin = registrationUiState.value.iin

                Log.d("bioOTP", "firebaseToken: $firebaseToken")
                Log.d("bioOTP", "phoneNumber: $phoneNumber")
                Log.d("bioOTP", "otpCode: $otpCode")
                Log.d("bioOTP", "iin: $iin")

                val bioOtpCheckRequest = BioOtpCheckRequest(
                    fcmToken = firebaseToken,
                    referal = "",
                    phone = phoneNumber,
                    iin = iin,
                    code = otpCode
                )

                Log.d("bioOTP", "Sending bioOtpCheck request")
                val result = registrationRepository.bioOtpCheck(bioOtpCheckRequest)
                Log.d("bioOTP", "bioOtpCheck result: $result")

                _bioCheckOTPResult.value = when (result) {
                    is BioCheckOTPResult.Success -> handleSuccessfulBioOtp(result)
                    is BioCheckOTPResult.Failure -> BioCheckOTPResult.Failure(ErrorResponse(result.error.msg))
                }
            } catch (e: Exception) {
                Log.e("bioOTP", "Failed to check Bio OTP", e)
                _bioCheckOTPResult.value = BioCheckOTPResult.Failure(ErrorResponse("Не удалось проверить код"))
            }
        }
    }
    fun handleCheckOtpResult(result: CheckOtpResult.Success) {
        viewModelScope.launch {
            userDataStoreManager.saveUserData(
                user = result.user,
                bonus = result.bonus,
                created = result.created,
                expired = result.expired,
                token = result.token
            )
            _isUserAuthenticated.value = true
            preferencesHelper.setUserAuthenticated(true)
        }
    }

    fun handleBioCheckOtpResult(result: BioCheckOTPResult.Success) {
        viewModelScope.launch {
            userDataStoreManager.saveUserData(
                user = result.user,
                bonus = result.bonus,
                created = result.created,
                expired = result.expired,
                token = result.token
            )
            _isUserAuthenticated.value = true
            preferencesHelper.setUserAuthenticated(true)
        }
    }

    fun setUserAuthenticated(isAuthenticated: Boolean) {
        _isUserAuthenticated.value = isAuthenticated
        preferencesHelper.setUserAuthenticated(isAuthenticated)
    }
    private suspend fun getFirebaseToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(task.exception ?: Exception("Failed to get Firebase token"))
            }
        }
    }

    private suspend fun handleSuccessfulOtp(result: CheckOtpResult.Success): CheckOtpResult {
        val userId = result.user.id
        val userType = registrationUiState.value.userType.value
        Log.d("user-type", "userType: $userType")

        return try {
            val updateResult = registrationRepository.universalUserUpdate(userId = userId, userType = userType)
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
    private suspend fun handleSuccessfulBioOtp(result: BioCheckOTPResult.Success): BioCheckOTPResult {
        val userId = result.user.id
        val userType = registrationUiState.value.userType.value
        Log.d("user-type", "userType: $userType")

        return try {
            val updateResult = registrationRepository.universalUserUpdate(userId = userId, userType = userType)
            Log.d("universalUserUpdate", "User update result: $updateResult")
            if (updateResult.success) {
                result
            } else {
                BioCheckOTPResult.Failure(ErrorResponse("Ошибка обновления пользователя"))
            }
        } catch (e: Exception) {
            Log.e("universalUserUpdate", "User update failed", e)
            BioCheckOTPResult.Failure(ErrorResponse("Ошибка обновления пользователя"))
        }
    }
    fun setUserIdentified(isIdentified: Boolean) {
        _isUserIdentified.value = isIdentified
        preferencesHelper.setUserIdentified(isIdentified)
    }
    fun logout() {
        viewModelScope.launch {
            userDataStoreManager.clearUserData() // Кнопка для выхода из аккаунта
            preferencesHelper.setUserAuthenticated(false)
            preferencesHelper.setUserIdentified(false)
            _isUserAuthenticated.value = false
            _isUserIdentified.value = false
        }
    }
    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerCount.intValue > 0) {
                delay(1000)
                _timerCount.intValue -= 1
                Log.d("TimerDebug", "Timer count in ViewModel: ${_timerCount.value}")
            }
        }
    }

    private fun resetTimer() {
        _timerCount.intValue = 59
        startTimer()
    }

    fun resendCode() {
        viewModelScope.launch {
            Log.d("checkPhone", "resendCode: ${registrationUiState.value.phone}")
            sendPhoneNumber("+7${registrationUiState.value.phone}")
            resetTimer()
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
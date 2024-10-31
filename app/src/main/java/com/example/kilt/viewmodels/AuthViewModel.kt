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
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.DeviceInfo
import com.example.kilt.data.authentification.ErrorResponse
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.data.authentification.UserWithMetadata
import com.example.kilt.data.dataStore.UserDataStoreManager
import com.example.kilt.data.shardePrefernce.PreferencesHelper
import com.example.kilt.enums.IdentificationTypes
import com.example.kilt.enums.UserType
import com.example.kilt.repository.LoginRepository
import com.example.kilt.repository.RegistrationRepository
import com.example.kilt.screens.profile.registration.AuthenticationUiState
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

    private val _authenticationUiState = mutableStateOf(AuthenticationUiState())
    val authenticationUiState:State<AuthenticationUiState> = _authenticationUiState

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

    private val _isUserIdentified = mutableStateOf(preferencesHelper.getUserIdentificationStatus())
    val isUserIdentified: State<IdentificationTypes> = _isUserIdentified

    private val _timerCount = mutableIntStateOf(59)
    val timerCount: State<Int> = _timerCount

    private var timerJob: Job? = null

    fun sendPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            _otpResult.value = loginRepository.handleOtpGeneration(phoneNumber)
        }
    }
    fun generateOTP(phoneNumber: String) {
        viewModelScope.launch {
            _otpResult.value = registrationRepository.handleOtpGeneration(phoneNumber)
        }
    }
    private fun checkOtp() {
        viewModelScope.launch {
            try {
                _checkOtpResult.value = loginRepository.handleCheckOtp(
                    phoneNumber = "+7${_authenticationUiState.value.phone}",
                    otpCode = _authenticationUiState.value.code.trim(),
                    firebaseToken = getFirebaseToken(),
                    userType = _authenticationUiState.value.userType.value
                )
            } catch (e: Exception) {
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
                        val phoneNumber = authenticationUiState.value.phone
                        val userType = authenticationUiState.value.userType
                        val iin = authenticationUiState.value.iin
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
                val phoneNumber = "7${authenticationUiState.value.phone}"
                val otpCode = authenticationUiState.value.code
                val iin = authenticationUiState.value.iin

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
        val userType = authenticationUiState.value.userType.value
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
        val userType = authenticationUiState.value.userType.value
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

    fun logout() {
        viewModelScope.launch {
            userDataStoreManager.clearUserData() // Кнопка для выхода из аккаунта
            preferencesHelper.setUserAuthenticated(false)
            _isUserAuthenticated.value = false
        }
    }
    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerCount.intValue > 0) {
                delay(1000)
                _timerCount.intValue -= 1
                Log.d("TimerDebug", "Timer count in ViewModel: ${_timerCount.intValue}")
            }
        }
    }

    private fun resetTimer() {
        _timerCount.intValue = 59
        startTimer()
    }

    fun resendCode() {
        viewModelScope.launch {
            Log.d("checkPhone", "resendCode: ${authenticationUiState.value.phone}")
            sendPhoneNumber("+7${authenticationUiState.value.phone}")
            resetTimer()
        }
    }
    fun updateForSixCode(newCode: String) {
        _authenticationUiState.value = authenticationUiState.value.copy(code = newCode)
        if (newCode.length == 6) {
            bioOtpCheck()
        }
    }
    fun updateForFourCode(newCode: String) {
        _authenticationUiState.value = authenticationUiState.value.copy(code = newCode)
        if (newCode.length == 4) {
            checkOtp()
        }
    }
    fun updatePhone(phone: String) {
        _authenticationUiState.value = authenticationUiState.value.copy(phone = phone)
    }
    fun updateIin(iin: String) {
        _authenticationUiState.value = authenticationUiState.value.copy(iin = iin)
    }
    fun updateUserType(userType: UserType) {
        _authenticationUiState.value = authenticationUiState.value.copy(userType = userType)
    }
    fun clearBioOtpResult(){
        _bioOtpResult.value = null
        _bioCheckOTPResult.value = null
        _authenticationUiState.value.code = ""
        resetTimer()
    }
    fun clearOtpResult(){
        _otpResult.value = null
        _checkOtpResult.value = null
        _authenticationUiState.value.code = ""
        resetTimer()
    }
    fun clear(){
        _authenticationUiState.value.phone = ""
    }
}
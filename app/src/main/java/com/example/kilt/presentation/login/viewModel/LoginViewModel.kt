package com.example.kilt.presentation.login.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.models.authentification.CheckOtpResult
import com.example.kilt.models.authentification.ErrorResponse
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.data.localstorage.sharedPreference.PreferencesHelper
import com.example.kilt.presentation.login.LoginUiState
import com.example.kilt.repository.LoginRepository
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferencesHelper: PreferencesHelper,
    private val userDataStoreManager: UserDataStoreManager,
) : ViewModel() {

    private val _otpResult = mutableStateOf<OtpResult?>(null)
    val otpResult: State<OtpResult?> = _otpResult

    private val _checkOtpResult = mutableStateOf<CheckOtpResult?>(null)
    val checkOtpResult: State<CheckOtpResult?> = _checkOtpResult

    private val _loginUiState = mutableStateOf(LoginUiState())
    val loginUiState: State<LoginUiState> = _loginUiState

    private val _isUserAuthenticated = mutableStateOf(preferencesHelper.isUserAuthenticated())
    val isUserAuthenticated: State<Boolean> = _isUserAuthenticated

    fun sendPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
//            _otpResult.value = loginRepository.handleOtpGeneration(phoneNumber)
        }
    }

    fun updateForFourCode(newCode: String) {
        _loginUiState.value = _loginUiState.value.copy(code = newCode)
        if (newCode.length == 4) {
            checkOtp()
        }
    }

    private fun checkOtp() {
        viewModelScope.launch {
            try {
                val result = loginRepository.handleCheckOtp(
                    phoneNumber = "+7${_loginUiState.value.phone}",
                    otpCode = _loginUiState.value.code.trim(),
                    firebaseToken = getFirebaseToken(),
                    userType = _loginUiState.value.userType.value
                )
                _checkOtpResult.value = result
                Log.d("LoginViewModel", "checkOtp: result = $result")
            } catch (e: Exception) {
                _checkOtpResult.value =
                    CheckOtpResult.Failure(ErrorResponse("Не удалось проверить код"))
                Log.e("LoginViewModel", "checkOtp error: ${e.message}")
            }
        }
    }

    fun setUserAuthenticated(isAuthenticated: Boolean) {
        _isUserAuthenticated.value = isAuthenticated
        preferencesHelper.setUserAuthenticated(isAuthenticated)
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

    fun clearOtpResult() {
        _otpResult.value = null
        _checkOtpResult.value = null
        _loginUiState.value.code = ""
//        resetTimer()
    }

    private suspend fun getFirebaseToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(
                    task.exception ?: Exception("Failed to get Firebase token")
                )
            }
        }
    }

    fun clear() {
        _loginUiState.value.phone = ""
    }
}
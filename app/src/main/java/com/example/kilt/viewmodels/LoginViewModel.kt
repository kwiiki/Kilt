package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.ErrorResponse
import com.example.kilt.data.OtpResult
import com.example.kilt.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val _otpResult = mutableStateOf<OtpResult?>(null)
    val otpResult: State<OtpResult?> = _otpResult

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
}
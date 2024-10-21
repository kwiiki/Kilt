package com.example.kilt.otp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor() : ViewModel() {
    private val _smsCode = MutableStateFlow("")
    val smsCode: StateFlow<String> = _smsCode.asStateFlow()

    fun startSmsRetriever(context: Context) {
        viewModelScope.launch {
            try {
                val smsRetrieverClient = SmsRetriever.getClient(context)
                smsRetrieverClient.startSmsUserConsent(null)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


    fun handleSmsIntent(intent: Intent?) {
        val message = intent?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
        message?.let { extractCodeFromMessage(it) }
    }

    fun handleSmsCode(code: String) {
        _smsCode.value = code
        Log.d("SMS_RETRIEVER", "SMS code updated in ViewModel: $code")
    }

    private fun extractCodeFromMessage(message: String) {
        val pattern = Pattern.compile("Код подтверждения в Kilt - (\\d{4})")
        val matcher = pattern.matcher(message)
        if (matcher.find()) {
            _smsCode.value = matcher.group(1) ?: ""
            Log.d("SMS_RETRIEVER", "Extracted code: ${_smsCode.value}")
        } else {
            Log.d("SMS_RETRIEVER", "Failed to extract code from message: $message")
        }
    }
}
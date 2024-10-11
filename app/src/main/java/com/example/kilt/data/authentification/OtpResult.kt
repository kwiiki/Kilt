package com.example.kilt.data.authentification

sealed class OtpResult {
    data class Success(val id: Int, val phone: String, val type: String, val createdAt: String) : OtpResult()
    data class Failure(val error: ErrorResponse) : OtpResult()
}

data class ErrorResponse(val msg: String)
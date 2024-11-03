package com.example.kilt.models.authentification

sealed class CheckOtpResult {
    data class Success(
        val expired: Boolean,
        val user: User,
        val token: String,
        val created: Boolean,
        val bonus: Int
    ) : CheckOtpResult()
    data class Failure(val error: ErrorResponse) : CheckOtpResult()
}

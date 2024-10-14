package com.example.kilt.data.authentification

sealed class BioCheckOTPResult() {
    data class Success(
        val bonus: Int,
        val created: Boolean,
        val expired: Boolean,
        val token: String,
        val user: User
    ): BioCheckOTPResult()
    data class Failure(val error: ErrorResponse) : BioCheckOTPResult()
}

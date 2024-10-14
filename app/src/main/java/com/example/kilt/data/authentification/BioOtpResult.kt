package com.example.kilt.data.authentification

sealed class BioOtpResult {
    data class Success(
        val register: Boolean,
        val success: Boolean,
        val message: String
    ) : BioOtpResult()

    data class Failure(
        val success: Boolean,
        val message: String
    ) : BioOtpResult()

    data class RegisteredUser(
        val id: Long,
        val phone: String,
        val type: String,
        val createdAt: String,
        val success: Boolean,
        val register: Boolean
    ) : BioOtpResult()
}





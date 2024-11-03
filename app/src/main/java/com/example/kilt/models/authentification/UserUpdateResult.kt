package com.example.kilt.models.authentification

sealed class UserUpdateResult {
    data class UpdateUserFailure(val success: Boolean, val message: String = "Update failed") : UserUpdateResult()
    data class UpdateUserSuccess(val success: Boolean, val user: User) : UserUpdateResult()
}

data class ApiResponse(
    val success: Boolean,
    val user: User? = null
)
package com.example.kilt.data.authentification

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
data class User(
    val agency_verification_status: Int,
    val agent_about: Any,
    val agent_city: Any,
    val agent_full_address: Any,
    val agent_working_hours: Any,
    val birthdate: Any,
    val createdAt: String,
    val deleted: Int,
    val discount: Int,
    val email: Any,
    val firstname: Any,
    val gender: Any,
    val id: Int,
    val iin: Any,
    val instagram: Any,
    val is_blocked: Boolean,
    val kr_agency_id: Any,
    val kr_agency_name: Any,
    val kr_listing_type: Any,
    val kr_name: Any,
    val kr_user_id: Any,
    val lastname: Any,
    val phone: String,
    val photo: Any,
    val referred_by: Any,
    val updatedAt: String,
    val user_type: String
)
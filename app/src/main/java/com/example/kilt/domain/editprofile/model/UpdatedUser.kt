package com.example.kilt.domain.editprofile.model

data class UpdatedUser(
    val agent_about: String = "",
    val agent_city: String ="",
    val agent_full_address: String ="",
    val agent_working_hours: String ="",
    val birthdate: String = "",
    val deleted: Int = 0,
    val is_blocked: Boolean = false,
    val lastname: String ="",
    val phone: String = "",
    val photo: String = "",
    val referred_by: String = "",
)

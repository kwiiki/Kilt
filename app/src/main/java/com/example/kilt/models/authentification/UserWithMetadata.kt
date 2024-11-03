package com.example.kilt.models.authentification

data class UserWithMetadata(
    val user: User,
    val bonus: Int,
    val created: Boolean,
    val expired: Boolean,
    val token: String
)
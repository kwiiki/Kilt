package com.example.kilt.data.authentification

data class UserWithMetadata(
    val user: User,
    val bonus: Int,
    val created: Boolean,
    val expired: Boolean,
    val token: String
)
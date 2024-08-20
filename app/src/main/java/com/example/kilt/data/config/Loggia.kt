package com.example.kilt.data.config

data class Loggia(
    val list: List<LoggiaItem>
)

data class LoggiaItem(
    val id: Int,
    val name: String
)
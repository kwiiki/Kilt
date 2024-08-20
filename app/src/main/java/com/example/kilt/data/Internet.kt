package com.example.kilt.data

data class Internet(
    val list: List<InternetItem>
)

data class InternetItem(
    val id: Int,
    val name: String
)

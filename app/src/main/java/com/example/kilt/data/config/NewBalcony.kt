package com.example.kilt.data.config

data class NewBalcony(
    val list: List<NewBalconyItem>
)

data class NewBalconyItem(
    val id: Int,
    val name: String
)
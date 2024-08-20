package com.example.kilt.data.config

data class Balcony(
    val list: List<BalconyItem>
)

data class BalconyItem(
    val id: Int,
    val name: String
)
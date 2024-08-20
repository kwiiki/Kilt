package com.example.kilt.data

data class HeatingList(
    val list: List<Heating>
)

data class Heating(
    val id: Int,
    val name: String
)
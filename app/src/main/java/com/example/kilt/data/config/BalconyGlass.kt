package com.example.kilt.data.config

data class BalconyGlass(
    val list: List<BalconyGlassItem>
)

data class BalconyGlassItem(
    val id: Int,
    val name: String
)
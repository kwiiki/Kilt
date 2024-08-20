package com.example.kilt.data

data class FormerDormitory(
    val list: List<FormerDormitoryItem>
)

data class FormerDormitoryItem(
    val id: Int,
    val name: String
)


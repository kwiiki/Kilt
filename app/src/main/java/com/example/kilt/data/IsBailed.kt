package com.example.kilt.data

data class IsBailed(
    val list: List<IsBailedItem>
)

data class IsBailedItem(
    val id: Int,
    val name: String
)
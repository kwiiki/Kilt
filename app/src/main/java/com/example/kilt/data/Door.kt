package com.example.kilt.data

data class Door(
    val list: List<DoorItem>
)

data class DoorItem(
    val id: Int,
    val name: String
)
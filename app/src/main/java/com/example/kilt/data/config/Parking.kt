package com.example.kilt.data.config

data class Parking(
    val list: List<ParkingItem>
)

data class ParkingItem(
    val id: Int,
    val name: String
)

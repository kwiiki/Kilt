package com.example.kilt.data.config

data class Parking(
    val list: List<ParkingItem>
)

data class ParkingItem(
    override val id:Int,override val name:String
):FilterItem()

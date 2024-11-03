package com.example.kilt.models.config

data class Parking(
    val list: List<ParkingItem>
)

data class ParkingItem(
    override val id:Int,override val name:String
):FilterItem()

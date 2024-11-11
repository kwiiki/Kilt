package com.example.kilt.domain.userabout.model

data class Listing(
    val id: String,
    val price: String,
    val numFloor: Int,
    val numFloors: Int,
    val numRooms: Int,
    val area:Double,
    val address:String,
    val image:String
)

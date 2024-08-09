package com.example.kilt.data

import okhttp3.Address

data class Home(
    val id: Int,
    val price: Int,
    val roomCount: Int,
    val homeArea: Int,
    val homeFloor: Int,
    val homeMaxFloor:Int,
    val address: String,
    val homeImg: Int

)

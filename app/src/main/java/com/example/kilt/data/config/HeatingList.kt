package com.example.kilt.data.config

data class HeatingList(
    val list: List<Heating>
)

data class Heating(
    override val id:Int,override val name:String
):FilterItem()
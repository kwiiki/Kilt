package com.example.kilt.data.config

data class Balcony(
    val list: List<BalconyItem>
)

data class BalconyItem(
    override val id:Int,override val name:String
):FilterItem()
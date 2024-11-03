package com.example.kilt.models.config

data class Balcony(
    val list: List<BalconyItem>
)

data class BalconyItem(
    override val id:Int,override val name:String
):FilterItem()
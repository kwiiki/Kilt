package com.example.kilt.data.config

data class NewBalcony(
    val list: List<NewBalconyItem>
)

data class NewBalconyItem(
    override val id:Int,override val name:String
):FilterItem()
package com.example.kilt.data.config

data class Door(
    val list: List<com.example.kilt.data.config.DoorItem>
)

data class DoorItem(
    override val id:Int,override val name:String
):FilterItem()
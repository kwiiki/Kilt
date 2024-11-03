package com.example.kilt.models.config

data class Door(
    val list: List<com.example.kilt.models.config.DoorItem>
)

data class DoorItem(
    override val id:Int,override val name:String
):FilterItem()
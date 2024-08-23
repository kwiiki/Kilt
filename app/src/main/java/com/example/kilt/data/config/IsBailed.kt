package com.example.kilt.data.config

data class IsBailed(
    val list: List<com.example.kilt.data.config.IsBailedItem>
)

data class IsBailedItem(
    override val id:Int,override val name:String
):FilterItem()
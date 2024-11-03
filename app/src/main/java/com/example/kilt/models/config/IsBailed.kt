package com.example.kilt.models.config

data class IsBailed(
    val list: List<com.example.kilt.models.config.IsBailedItem>
)

data class IsBailedItem(
    override val id:Int,override val name:String
):FilterItem()
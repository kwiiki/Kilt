package com.example.kilt.models.config

data class Internet(
    val list: List<com.example.kilt.models.config.InternetItem>
)

data class InternetItem(
    override val id:Int,override val name:String
):FilterItem()

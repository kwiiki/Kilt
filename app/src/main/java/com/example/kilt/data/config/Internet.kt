package com.example.kilt.data.config

data class Internet(
    val list: List<com.example.kilt.data.config.InternetItem>
)

data class InternetItem(
    override val id:Int,override val name:String
):FilterItem()

package com.example.kilt.data.config


data class BathroomInside(
    val list: List<com.example.kilt.data.config.BathroomInsideItem>
)

data class BathroomInsideItem(
    override val id:Int,override val name:String
):FilterItem()
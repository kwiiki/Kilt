package com.example.kilt.models.config


data class BathroomInside(
    val list: List<com.example.kilt.models.config.BathroomInsideItem>
)

data class BathroomInsideItem(
    override val id:Int,override val name:String
):FilterItem()
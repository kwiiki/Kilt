package com.example.kilt.models.config

data class BalconyGlass(
    val list: List<BalconyGlassItem>
)

data class BalconyGlassItem(
    override val id:Int,override val name:String
):FilterItem()
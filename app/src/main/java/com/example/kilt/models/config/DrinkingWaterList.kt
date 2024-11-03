package com.example.kilt.models.config

data class DrinkingWaterList(val list: List<com.example.kilt.models.config.DrinkingWater>)

data class DrinkingWater( override val id:Int,override val name:String
):FilterItem()
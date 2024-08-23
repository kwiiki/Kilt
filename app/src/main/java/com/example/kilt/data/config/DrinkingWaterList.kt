package com.example.kilt.data.config

data class DrinkingWaterList(val list: List<com.example.kilt.data.config.DrinkingWater>)

data class DrinkingWater( override val id:Int,override val name:String
):FilterItem()
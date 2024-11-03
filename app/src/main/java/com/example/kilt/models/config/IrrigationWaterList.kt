package com.example.kilt.models.config

data class IrrigationWaterList(val list: List<com.example.kilt.models.config.IrrigationWater>)

data class IrrigationWater( override val id:Int,override val name:String
):FilterItem()
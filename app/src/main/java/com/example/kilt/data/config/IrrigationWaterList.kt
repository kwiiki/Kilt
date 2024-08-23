package com.example.kilt.data.config

data class IrrigationWaterList(val list: List<com.example.kilt.data.config.IrrigationWater>)

data class IrrigationWater( override val id:Int,override val name:String
):FilterItem()
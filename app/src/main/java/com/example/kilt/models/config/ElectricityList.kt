package com.example.kilt.models.config

data class ElectricityList(val list: List<Electricity>)

data class Electricity( override val id:Int,override val name:String
):FilterItem()
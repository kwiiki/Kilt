package com.example.kilt.data.config

data class ElectricityList(val list: List<Electricity>)

data class Electricity( override val id:Int,override val name:String
):FilterItem()
package com.example.kilt.models.config

data class HouseLineList(val list: List<HouseLine>)

data class HouseLine(override val id:Int, override val name:String
):FilterItem(){

}

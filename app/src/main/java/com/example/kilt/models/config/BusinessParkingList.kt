package com.example.kilt.models.config

data class BusinessParkingList(val list:List<BusinessParking>)

data class BusinessParking( override val id:Int,override val name:String
):FilterItem()

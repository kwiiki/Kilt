package com.example.kilt.data.config

data class BusinessEntranceList(val list:List<  BusinessEntrance>)

data class BusinessEntrance( override val id:Int,override val name:String
):FilterItem()

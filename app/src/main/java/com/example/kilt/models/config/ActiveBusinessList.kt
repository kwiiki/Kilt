package com.example.kilt.models.config

data class ActiveBusinessList(val list:List<ActiveBusiness>)

data class ActiveBusiness(override val id:Int,override val name:String):FilterItem()

package com.example.kilt.data.config

data class BusinessConditionList(val list:List<com.example.kilt.data.config.BusinessCondition>)

data class BusinessCondition( override val id:Int,override val name:String
):FilterItem()

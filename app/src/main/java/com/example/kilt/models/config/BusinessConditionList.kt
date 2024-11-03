package com.example.kilt.models.config

data class BusinessConditionList(val list:List<com.example.kilt.models.config.BusinessCondition>)

data class BusinessCondition( override val id:Int,override val name:String
):FilterItem()

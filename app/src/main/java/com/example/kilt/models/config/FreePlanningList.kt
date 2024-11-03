package com.example.kilt.models.config

data class FreePlanningList(val list:List<com.example.kilt.models.config.FreePlanning>)

data class FreePlanning( override val id:Int,override val name:String
):FilterItem()

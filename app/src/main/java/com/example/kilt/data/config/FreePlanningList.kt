package com.example.kilt.data.config

data class FreePlanningList(val list:List<com.example.kilt.data.config.FreePlanning>)

data class FreePlanning( override val id:Int,override val name:String
):FilterItem()

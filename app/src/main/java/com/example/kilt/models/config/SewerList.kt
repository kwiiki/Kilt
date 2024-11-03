package com.example.kilt.models.config

data class SewerList(val list:List<com.example.kilt.models.config.Sewer>)

data class Sewer( override val id:Int,override val name:String
):FilterItem()
package com.example.kilt.data.config

data class SewerList(val list:List<com.example.kilt.data.config.Sewer>)

data class Sewer( override val id:Int,override val name:String
):FilterItem()
package com.example.kilt.data.config

data class FormerDormitory(
    val list: List<com.example.kilt.data.config.FormerDormitoryItem>
)

data class FormerDormitoryItem(
    override val id:Int,override val name:String
):FilterItem()

package com.example.kilt.models.config

data class FormerDormitory(
    val list: List<com.example.kilt.models.config.FormerDormitoryItem>
)

data class FormerDormitoryItem(
    override val id:Int,override val name:String
):FilterItem()

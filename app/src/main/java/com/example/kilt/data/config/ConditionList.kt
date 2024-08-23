package com.example.kilt.data.config

data class Condition(
    override val id:Int,override val name:String
):FilterItem()

data class ConditionList(
    val list: List<com.example.kilt.data.config.Condition>
)
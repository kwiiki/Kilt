package com.example.kilt.models.config

data class Condition(
    override val id:Int,override val name:String
):FilterItem()

data class ConditionList(
    val list: List<Condition>
)
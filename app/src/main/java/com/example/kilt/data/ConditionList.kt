package com.example.kilt.data

data class Condition(
    val id: Int,
    val name: String
)

data class ConditionList(
    val list: List<Condition>
)
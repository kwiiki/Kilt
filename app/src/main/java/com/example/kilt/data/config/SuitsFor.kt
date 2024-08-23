package com.example.kilt.data.config

data class SuitsFor(
    val list: List<SuitsForItem>
)

data class SuitsForItem(
    override val id:Int,override val name:String
):FilterItem()
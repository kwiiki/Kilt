package com.example.kilt.data.config

data class SuitsFor(
    val list: List<SuitsForItem>
)

data class SuitsForItem(
    val id: Int,
    val name: String
)
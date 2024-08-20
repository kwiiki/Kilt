package com.example.kilt.data


data class BathroomInside(
    val list: List<BathroomInsideItem>
)

data class BathroomInsideItem(
    val id: Int,
    val name: String
)
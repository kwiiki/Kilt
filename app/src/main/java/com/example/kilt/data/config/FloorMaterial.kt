package com.example.kilt.data.config

data class FloorMaterial(
    val list: List<FloorMaterialItem>
)

data class FloorMaterialItem(
    val id: Int,
    val name: String
)

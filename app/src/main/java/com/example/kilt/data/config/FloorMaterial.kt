package com.example.kilt.data.config

data class FloorMaterial(
    val list: List<FloorMaterialItem>
)

data class FloorMaterialItem(
    override val id:Int,override val name:String
):FilterItem()

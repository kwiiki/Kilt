package com.example.kilt.models.config

data class Windows(
    val list: List<WindowsItem>
)

data class WindowsItem(
    override val id:Int,override val name:String
):FilterItem()
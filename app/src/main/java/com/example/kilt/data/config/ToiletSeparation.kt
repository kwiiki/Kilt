package com.example.kilt.data.config

data class ToiletSeparation(
    val list: List<ToiletSeparationItem>
)

data class ToiletSeparationItem(
    val id: Int,
    val name: String
)
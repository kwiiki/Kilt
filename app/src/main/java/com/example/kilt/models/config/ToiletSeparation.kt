package com.example.kilt.models.config

data class ToiletSeparation(
    val list: List<ToiletSeparationItem>
)

data class ToiletSeparationItem(
    override val id: Int,
    override val name: String
) :FilterItem()
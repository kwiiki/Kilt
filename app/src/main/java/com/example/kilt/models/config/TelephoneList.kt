package com.example.kilt.models.config

data class TelephoneList(val list: List<Telephone>)

data class Telephone(override val id:Int,override val name: String):FilterItem()
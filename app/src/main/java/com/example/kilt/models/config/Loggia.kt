package com.example.kilt.models.config

data class Loggia(
    val list: List<LoggiaItem>
)

data class LoggiaItem(
    override val id:Int,override val name:String
):FilterItem()
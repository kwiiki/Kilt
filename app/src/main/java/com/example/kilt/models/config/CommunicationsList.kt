package com.example.kilt.models.config

data class CommunicationsList(val list: List<Communications>)

data class Communications( override val id:Int,override val name:String
):FilterItem()

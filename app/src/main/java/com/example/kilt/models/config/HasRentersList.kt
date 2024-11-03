package com.example.kilt.models.config

data class HasRentersList(val list:List<HasRenters>)

data class HasRenters( override val id:Int,override val name:String
):FilterItem()
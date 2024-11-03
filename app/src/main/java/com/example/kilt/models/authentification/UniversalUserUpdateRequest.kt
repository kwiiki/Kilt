package com.example.kilt.models.authentification

data class UniversalUserUpdateRequest(val filters:Filters,val update: Update)


data class Filters(val id:Int)
data class Update(val user_type :String)
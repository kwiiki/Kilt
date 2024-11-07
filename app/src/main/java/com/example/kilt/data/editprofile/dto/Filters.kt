package com.example.kilt.data.editprofile.dto

import com.google.gson.annotations.SerializedName


data class UserPhone(val filters: Filters)
data class Filters(
    @SerializedName("user_id")
    val userId:String
)
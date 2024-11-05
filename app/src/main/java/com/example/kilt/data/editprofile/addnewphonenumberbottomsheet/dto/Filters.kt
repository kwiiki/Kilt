package com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto

data class Filters(
    val phone: String,
    val code: String,
    val type: String = "user_phone"
)
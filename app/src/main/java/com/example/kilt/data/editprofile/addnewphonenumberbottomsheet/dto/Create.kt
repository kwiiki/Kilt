package com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto


data class Send(val create: Create)
data class Create(
    val user_id: Int,
    val phone: String,
)

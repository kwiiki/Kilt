package com.example.kilt.data.authentification

data class BioOtpCheckRequest(
    val phone:String,
    val code:String,
    val iin:String,
    val fcmToken:String,
    val referal:String = ""
)

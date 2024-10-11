package com.example.kilt.data.authentification

data class CheckOtpRequest(
    val checkOtp: CheckOtp,
    val fcmToken: String,
    val referal:String = ""
)

package com.example.kilt.data.authentification

data class CheckOtpRequest(
    val otp: CheckOtp,
    val fcmToken: String,
    val referal:String = ""
)

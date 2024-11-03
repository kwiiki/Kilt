package com.example.kilt.models.authentification

data class CheckOtpRequest(
    val otp: CheckOtp,
    val fcmToken: String,
    val referal:String = ""
)

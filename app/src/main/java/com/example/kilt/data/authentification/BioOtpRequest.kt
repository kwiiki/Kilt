package com.example.kilt.data.authentification

import com.google.gson.annotations.SerializedName


data class BioOtpRequest(
    val fcmToken: String,
    @SerializedName("device_info")
    val deviceInfo: DeviceInfo,
    val phone: String,
    val iin: String,
    @SerializedName("user_type")
    val userType: String
)

data class DeviceInfo(
    @SerializedName("app_version")
    val appVersion: String,
    val os: String = "Android"
)
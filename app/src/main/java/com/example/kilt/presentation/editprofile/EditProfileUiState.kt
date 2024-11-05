package com.example.kilt.presentation.editprofile

data class EditProfileUiState(
    var userImage:String = "",
    val firstname:String = "",
    var userAbout:String = "",
    val firstPhoneNumber:String = "",
    var secondPhoneNumber:String = "",
    var userCity:String = "",
    var userFullAddress:String = "",
    var userWorkHour:String = "",
    var code:String = ""
)

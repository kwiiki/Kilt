package com.example.kilt.presentation.login

import com.example.kilt.enums.UserType

data class LoginUiState(
    var phone: String = "",
    var code: String = "",
    var codeLength:Int = 4,
    var userType: UserType = UserType.OWNER,
)
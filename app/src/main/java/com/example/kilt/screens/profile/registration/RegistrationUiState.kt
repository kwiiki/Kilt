package com.example.kilt.screens.profile.registration

import com.example.kilt.enums.UserType

data class RegistrationUiState(
    var phone: String = "",
    val iin: String = "",
    var userType: UserType = UserType.OWNER,
    var code: String = ""
)

package com.example.kilt.screens.profile.registration

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.models.authentification.OtpResult
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.screens.profile.login.PhoneNumberTextField
import com.example.kilt.presentation.listing.gradient
import com.example.kilt.presentation.login.viewModel.AuthViewModel

@Composable
fun RegistrationForAgencyPage(navController: NavHostController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val bottomPadding = if (imeVisible) 1.dp else 16.dp
    val focusManager = LocalFocusManager.current
    val otpResult by authViewModel.otpResult
    val registrationUiState = authViewModel.authenticationUiState.value
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val enabledBoolean = registrationUiState.phone.length == 10

    LaunchedEffect(otpResult) {
        otpResult?.let {
            Log.d("loginPage", "LoginPage: $it")
            when (it) {
                is OtpResult.Success -> {
                    navController.navigate(NavPath.ENTERFOURCODEPAGE.name)
                }
                is OtpResult.Failure -> {
                    errorMessage = it.error.msg
                    showError = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Row {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .clickable { navController.popBackStack() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Регистрация",
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Введите номер телефона чтобы зарегистрироваться",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
                PhoneNumberTextField(
                    value = registrationUiState.phone,
                    onValueChange = {
                        registrationUiState.phone = it
                        isError = it.length != 10 // Update error state based on the length
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    focusManager = focusManager,
                    showError = showError
                )
                if (showError) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                } else if (isError) {

                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            val borderColor by remember {
                derivedStateOf {
                    if (enabledBoolean ) Color(0xFF3244E4) else Color(0xffEFF1F4)
                }
            }
            Log.d("phone length", "RegistrationForAgencyPage: ${registrationUiState.phone.length}")
            OutlinedButton(
                onClick = {
                    if (registrationUiState.phone.length < 10) {
                        isError = true
                        errorMessage = "Введите корректный номер"
                    } else {
                        authViewModel.generateOTP("+7${registrationUiState.phone}")
                        isError = false
                        errorMessage = ""
                    }
                },
                contentPadding = PaddingValues(0.dp),
                enabled = enabledBoolean,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 1.dp, color = borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        if (enabledBoolean) gradient else enabledGradient,
                        RoundedCornerShape(12.dp)
                    )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Продолжить",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.profile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.models.authentification.BioCheckOTPResult
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.presentation.login.viewModel.AuthViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun EnterSixCodePage(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val authenticationUiState = authViewModel.authenticationUiState
    val bioCheckOTPResult by authViewModel.bioCheckOTPResult
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val timerCount by authViewModel.timerCount
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val bottomPadding = if (imeVisible) 1.dp else 16.dp

    LaunchedEffect(Unit) {
        authViewModel.startTimer()
    }
    LaunchedEffect(bioCheckOTPResult) {
        bioCheckOTPResult?.let {
            Log.d("loginPage", "LoginPage: $it")
            when (it) {
                is BioCheckOTPResult.Success -> {
                    navController.navigate(NavPath.PROFILE.name)
                    authViewModel.setUserAuthenticated(true)
                    authViewModel.handleBioCheckOtpResult(it)
                }
                is BioCheckOTPResult.Failure -> {
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color(0xFF566982),
                    modifier = Modifier
                        .size(55.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Введите код из SMS",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.align(Alignment.Start)
                )
                val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
                val number = "7${authenticationUiState.value.phone}"
                val output = regex.replace(number, "$1 $2 $3 $4 $5")
                Text(
                    text = "На ваш телефон +$output отправили SMS с кодом подтверждения. Пожалуйста, введите его ниже, чтобы войти в ваш аккаунт.",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = authenticationUiState.value.code,
                        onValueChange = { newCode ->
                            if (newCode.length <= 6) {
                                authViewModel.updateForSixCode(newCode)
                            }
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = if (showError) Color.Red else Color(0xFFcfcfcf),
                        focusedBorderColor = if (showError) Color.Red else Color(0xFFcfcfcf),
                        cursorColor = Color(0xFFDBDFE4)
                    ),
                    label = {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.Gray)) {
                                    append("SMS код")
                                }
                                withStyle(style = SpanStyle(color = Color.Red)) {
                                    append("*")
                                }
                            }
                        )
                    }
                )
                if (showError) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding, start = 16.dp, end = 16.dp)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            val textColor by remember {
                derivedStateOf {
                    if (timerCount > 0) Color(0xffBEC1CC) else Color(0xff3244E4)
                }
            }
            val backgroundColor by remember {
                derivedStateOf {
                    if (timerCount > 0) Color(0xffEFF1F4) else Color(0xFFFFFFFF)
                }
            }
            val borderColor by remember {
                derivedStateOf {
                    if (timerCount > 0) Color(0xffEFF1F4) else Color(0xFF3244E4)
                }
            }
            OutlinedButton(
                onClick = {
                    authViewModel.resendCode()
                },
                enabled = timerCount == 0,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 1.dp, color = borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(backgroundColor, RoundedCornerShape(12.dp))
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
                            text = if (timerCount > 0) {"Повторить через $timerCount секунд"} else "Запросить еще раз",
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEnterSixCode() {
    EnterSixCodePage(
        navController = rememberNavController(),
        authViewModel = hiltViewModel()
    )
}
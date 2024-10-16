package com.example.kilt.screens.profile

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.navigation.NavPath
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun EnterFourCodePage(navController: NavHostController, authViewModel: AuthViewModel) {
    val registrationUiState = authViewModel.registrationUiState.value
    val checkOtpResult by authViewModel.checkOtpResult
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val timerCount by authViewModel.timerCount

    LaunchedEffect(Unit) {
        authViewModel.startTimer()
    }
    LaunchedEffect(checkOtpResult) {
        checkOtpResult?.let {
            Log.d("loginPage", "LoginPage: $it")
            when (it) {
                is CheckOtpResult.Success -> {
                    navController.navigate(NavPath.PROFILE.name)
                    authViewModel.setUserAuthenticated(true)
                    authViewModel.handleCheckOtpResult(it)
                }
                is CheckOtpResult.Failure -> {
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
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color(0xFF566982),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.15f))
                Text(
                    text = "Подтверждение личности",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Введите код",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.align(Alignment.Start)
                )
                val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
                val number = "7${registrationUiState.phone}"
                val output = regex.replace(number, "$1 $2 $3 $4 $5")
                Text(
                    text = "Код отправлен на номер: +$output",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = registrationUiState.code,
                    onValueChange = { newCode ->
                        if (newCode.length <= 4) {
                            authViewModel.updateForFourCode(newCode)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(14.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDBDFE4),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .height(50.dp),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            val textColor by remember {
                derivedStateOf {
                    if (timerCount > 0) Color(0xff8794A5) else Color(0xff010101)
                }
            }
            Text(
                text = if (timerCount > 0)
                    "Отправить код повторно через $timerCount сек"
                else
                    "Отправить код повторно",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 20.sp,
                color = textColor,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(enabled = timerCount == 0) {
                        if (timerCount == 0) {
                            authViewModel.resendCode()
                        }
                    }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEnterCodePage() {
    val navController = rememberNavController()
    EnterFourCodePage(navController, authViewModel = hiltViewModel())
}
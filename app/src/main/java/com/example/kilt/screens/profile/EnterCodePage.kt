package com.example.kilt.screens.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
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
import com.example.kilt.viewmodels.LoginViewModel

@Composable
fun EnterCodePage(navController: NavHostController,loginViewModel: LoginViewModel) {
    val scrollState = rememberScrollState()
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val loginUiState = loginViewModel.loginUiState.value
    val checkOtpResult by loginViewModel.checkOtpResult
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(checkOtpResult) {
        checkOtpResult?.let {
            Log.d("loginPage", "LoginPage: $it")
            when (it) {
                is CheckOtpResult.Success -> {
                    navController.navigate(NavPath.PROFILE.name)
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
                .verticalScroll(scrollState)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Введите код",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.align(Alignment.Start)
                )
                val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
                val number = "7${loginUiState.phone}"
                val output = regex.replace(number, "$1 $2 $3 $4 $5")
                Text(
                    text = "Код отправлен на номер: +$output",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = loginUiState.code,
                    onValueChange = { newCode ->
                        if (newCode.length <= 4) {
                            loginViewModel.updateCode(newCode)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEnterCodePage() {
    val navController = rememberNavController()
    EnterCodePage(navController, loginViewModel = hiltViewModel())
}
package com.example.kilt.screens.profile.registration

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.models.authentification.BioOtpResult
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.screens.profile.login.PhoneNumberTextField
import com.example.kilt.presentation.listing.gradient
import com.example.kilt.presentation.login.viewModel.AuthViewModel

val enabledGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFBEC1CC), Color(0xFFBEC1CC))
)

@Composable
fun RegistrationForOwnerPage(navController: NavHostController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val bottomPadding = if (imeVisible) 1.dp else 16.dp
    val focusManager = LocalFocusManager.current
    val bioOtpResult by authViewModel.bioOtpResult
    val registrationUiState = authViewModel.authenticationUiState.value
    var selected by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    LaunchedEffect(bioOtpResult) {
        bioOtpResult?.let {
            Log.d("loginPage", "LoginPage: $it")
            when (it) {
                is BioOtpResult.Success -> {
                    authViewModel.clearBioOtpResult()
                    navController.navigate(NavPath.ENTERSIXCODEPAGE.name)
                }
                is BioOtpResult.Failure -> {
                    errorMessage = it.message
                    showError = true
                }
                is BioOtpResult.RegisteredUser -> {
                    Log.d("lool", "OwnerPage: ${registrationUiState.phone}")
                    navController.navigate(NavPath.ENTERFOURCODEPAGE.name)
                    authViewModel.sendPhoneNumber("+7${registrationUiState.phone}")
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
                        .clickable {
                            navController.popBackStack()
                            authViewModel.clear()
                        }
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
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Введите номер телефона и ИИН чтобы зарегистрироваться",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
                PhoneNumberTextField(
                    value = registrationUiState.phone,
                    onValueChange = { authViewModel.updatePhone(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    focusManager = focusManager,
                    showError = showError
                )
                OutlinedTextField(
                    value = registrationUiState.iin,
                    onValueChange = { if (it.length <= 12) authViewModel.updateIin(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    singleLine = true,
                    isError = isError,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (showError) Color.Red else Color(0xFFcfcfcf),
                        focusedBorderColor = if (showError) Color.Red else Color(0xFFcfcfcf),
                        cursorColor = Color.Black,
                        errorBorderColor = Color.Red
                    ),
                    label = {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.Gray)) {
                                    append("ИИН")
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFBEC1CC),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(color = Color(0xFFEFF1F4))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Внимание",
                        fontWeight = FontWeight.W700,
                        fontSize = 14.sp,
                        color = Color(0xFF01060E)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Для защиты от мошенничества ваш номер телефона должен быть зарегистрирован в системе eGov. \n" +
                                "Это помогает нам убедиться, что учетная запись принадлежит реальному человеку.",
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = Color(0xFF01060E)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFC4C9D3),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(color = Color.White)
                        .padding(12.dp)
                ) {
                    Checkbox(
                        checked = selected,
                        onCheckedChange = { selected = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF3244E4),
                            uncheckedColor = Color(0xFFBEC1CC),
                            checkmarkColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Регистрируясь, я соглашаюсь на обработку персональных данных и принимаю условия соглашения и Политики конфиденциальности.",
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = Color(0xFF01060E)
                    )
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
            OutlinedButton(
                onClick = {
                    showError = false
                    errorMessage = ""
                    authViewModel.clearBioOtpResult()
                    if (registrationUiState.phone.length < 10) {
                        isError = true
                        showError = true
                        errorMessage = "Введите корректный номер"
                    } else {
                        Log.d("phone size", "RegistrationForOwnerPage: ${registrationUiState.phone.length}")
                        authViewModel.bioOtp()
                        isError = false
                        showError = false
                        errorMessage = ""
                    }
                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp),
                enabled = selected && registrationUiState.phone.length == 10 && registrationUiState.iin.length == 12,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        if (registrationUiState.phone.length == 10 && selected && registrationUiState.iin.length == 12) gradient else enabledGradient,
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
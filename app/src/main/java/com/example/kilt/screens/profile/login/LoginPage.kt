package com.example.kilt.screens.profile.login

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.data.authentification.OtpResult
import com.example.kilt.navigation.NavPath
import com.example.kilt.screens.profile.registration.RegistrationButton
import com.example.kilt.screens.searchpage.homedetails.gradient
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun LoginPage(navController: NavHostController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val bottomPadding = if (imeVisible) 1.dp else 16.dp
    val focusManager = LocalFocusManager.current
    val otpResult by authViewModel.otpResult

    val registrationUiState = authViewModel.registrationUiState.value

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

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
                        .clickable { navController.popBackStack()
                                        authViewModel.clear()}
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
                    text = "Вход",
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Введите номер телефона чтобы войти",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
                PhoneNumberTextField(
                    value = registrationUiState.phone,
                    onValueChange = { registrationUiState.phone = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    focusManager = focusManager
                )
                if (showError) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                } else if(isError){
                    Text(
                        text = "Введите корректный номер",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 85.dp)
                .padding(horizontal = 30.dp),
        ) {
            Text(
                text = "При регистрации аккаунта я даю согласие на обработку своих персональных данных, принимаю условия пользовательского соглашения и Политики конфиденциальности.",
                fontSize = 12.sp,
                lineHeight = 20.sp,
                color = Color(0xff566982),
                fontWeight = FontWeight.W700,
                modifier = Modifier.align(Alignment.Center)
            )
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
                    if (registrationUiState.phone.length < 10) {
                        isError = true
                        errorMessage = "Введите корректный номер"
                    } else {
                        authViewModel.sendPhoneNumber("+7${registrationUiState.phone}")
                        isError = false
                        errorMessage = ""
                    }
                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(gradient, RoundedCornerShape(12.dp))
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
            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                RegistrationButton(navController = navController,modifier = Modifier)
            }
        }
    }
}
@Composable
fun PhoneNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager
) {
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = if (value.isNotEmpty()) formatPhoneNumber(value) else "",
                selection = TextRange(if (value.isNotEmpty()) formatPhoneNumber(value).length else 0)
            )
        )
    }
    var isFocused by remember { mutableStateOf(false) }
    val isError by remember { mutableStateOf(false) }
    val errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        value = textFieldValueState,
        onValueChange = { newValue ->
            val oldText = textFieldValueState.text
            val newText = if (isFocused && !newValue.text.startsWith("+7")) "+7${newValue.text}" else newValue.text
            val oldSelection = textFieldValueState.selection.start
            val newSelection = newValue.selection.start

            val unformattedNewText = newText.substringAfter("+7").filter { it.isDigit() }

            if (unformattedNewText.length <= 10) {
                val formattedNewText = if (isFocused) formatPhoneNumber(unformattedNewText) else unformattedNewText

                val oldFormattedCursorPosition =
                    oldText.take(oldSelection).count { it.isDigit() } - if (isFocused) 1 else 0
                val newUnformattedCursorPosition =
                    newText.take(newSelection).count { it.isDigit() } - if (isFocused) 1 else 0

                val cursorOffset = newUnformattedCursorPosition - oldFormattedCursorPosition

                val newCursorPosition = formattedNewText.mapIndexed { index, c ->
                    if (c.isDigit() && (if (isFocused) index > 1 else true)) index else -1
                }.filter { it != -1 }.getOrNull(oldFormattedCursorPosition + cursorOffset)
                    ?: formattedNewText.length

                textFieldValueState = TextFieldValue(
                    text = formattedNewText,
                    selection = TextRange(newCursorPosition)
                )
                onValueChange(unformattedNewText)
            }
        },
        modifier = modifier.onFocusChanged { focusState ->
            if (focusState.isFocused && !isFocused) {
                isFocused = true
                if (!textFieldValueState.text.startsWith("+7")) {
                    textFieldValueState = TextFieldValue(
                        text = "+7${textFieldValueState.text}",
                        selection = TextRange(textFieldValueState.text.length + 2)
                    )
                }
            } else if (!focusState.isFocused) {
                isFocused = false
                if (textFieldValueState.text == "+7") {
                    textFieldValueState = TextFieldValue(text = "")
                }
            }
        },
        singleLine = true,
        isError = isError,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = if (isError) Color.Red else Color(0xFFcfcfcf),
            focusedBorderColor = if (isError) Color.Red else Color(0xFFcfcfcf),
            cursorColor = Color.Black,
            errorBorderColor = Color.Red
        ),
        label = {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("Номер телефона")
                    }
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("*")
                    }
                }
            )
        },
    )
    if (isError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
fun formatPhoneNumber(digits: String): String {
    return buildString {
        append("+7")
        if (digits.isNotEmpty()) {
            append("(")
            append(digits.take(3))
            if (digits.length > 3) {
                append(") ")
                append(digits.substring(3, minOf(digits.length, 6)))
                if (digits.length > 6) {
                    append(" ")
                    append(digits.substring(6, minOf(digits.length, 8)))
                    if (digits.length > 8) {
                        append(" ")
                        append(digits.substring(8, minOf(digits.length, 10)))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginPage() {
    val navController = rememberNavController()
    LoginPage(navController = navController, authViewModel = hiltViewModel())
}
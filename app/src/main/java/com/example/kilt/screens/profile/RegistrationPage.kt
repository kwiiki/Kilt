package com.example.kilt.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.screens.searchpage.homedetails.gradient
import com.example.kilt.viewmodels.LoginViewModel
import androidx.compose.runtime.remember

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RegistrationPage(navController: NavHostController, loginViewModel: LoginViewModel) {
    var selectedUserType by remember { mutableStateOf<UserType?>(null) }
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
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
                    text = "Укажите тип аккаунта",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Выберите один вариант",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp, vertical = 140.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { selectedUserType = UserType.OWNER }
                        .padding(vertical = 8.dp)// Добавляем одинаковые отступы для всех Row
                ) {
                    Text(
                        text = UserType.OWNER.ruText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = if (selectedUserType == UserType.OWNER) Color(0xFF3F4FE0) else Color.Black,
                        modifier = Modifier.weight(1f) // Используем Modifier для управления текстом
                    )
                    if (selectedUserType == UserType.OWNER) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                            contentDescription = "Selected",
                            tint = Color(0xFF3F4FE0),
                            modifier = Modifier.size(24.dp) // Фиксируем размер иконки
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ){ selectedUserType = UserType.AGENT }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = UserType.AGENT.ruText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = if (selectedUserType == UserType.AGENT) Color(0xFF3F4FE0) else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (selectedUserType == UserType.AGENT) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                            contentDescription = "Selected",
                            tint = Color(0xFF3F4FE0),
                            modifier = Modifier.size(24.dp) // Фиксируем размер иконки
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ){ selectedUserType = UserType.AGENCY }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = UserType.AGENCY.ruText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = if (selectedUserType == UserType.AGENCY) Color(0xFF3F4FE0) else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (selectedUserType == UserType.AGENCY) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                            contentDescription = "Selected",
                            tint = Color(0xFF3F4FE0),
                            modifier = Modifier.size(24.dp)
                        )
                    }
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
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            OutlinedButton(
                onClick = {
                    when (selectedUserType) {
                        UserType.OWNER -> {
                            navController.navigate(NavPath.OWNERPAGE.name)
                        }
                        UserType.AGENT -> {
                            navController.navigate(NavPath.OWNERPAGE.name)
                        }
                        UserType.AGENCY -> {
                            navController.navigate(NavPath.AGENCYPAGE.name)
                        }
                        else -> {
                            // handle case where no selection is made (optional)
                        }
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
        }
    }
}

@Composable
fun UserTypeRow(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = if (isSelected) Color(0xFF3F4FE0) else Color(0xFF010101)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                contentDescription = null,
                tint = Color(0xFF3F4FE0)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewRegistrationPage() {
    RegistrationPage(navController = rememberNavController(), loginViewModel = hiltViewModel())
}

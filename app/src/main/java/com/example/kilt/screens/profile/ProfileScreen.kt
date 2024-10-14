package com.example.kilt.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.screens.profile.login.LoginButton
import com.example.kilt.screens.profile.registration.RegistrationButton


@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Профиль",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 36.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.W800

        )
        LoginSection(navController)
        Spacer(modifier = Modifier.height(24.dp))
        SettingsSection()
    }
}

@Composable
fun LoginSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Войдите чтобы расширить возможности и следить за поисками",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(8.dp))
        LoginButton(navController,Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        RegistrationButton(navController,Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun SettingsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.document_icon), // Replace with your custom icon
            title = "Блог"
        )
        Text(
            text = "О проекте",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp),
            fontSize = 24.sp
        )
        SettingItem(
            icon = null,
            title = "Политика конфиденциальности"
        )
        SettingItem(
            icon = null,
            title = "Пользовательское соглашение"
        )
        SettingItem(
            icon = null,
            title = "Связаться с нами"
        )
        SettingItem(
            icon = null,
            title = "Правила"
        )
    }
}

@Composable
fun SettingItem(icon: ImageVector?, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Implement click handling */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xff566982)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // Use a right arrow icon
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

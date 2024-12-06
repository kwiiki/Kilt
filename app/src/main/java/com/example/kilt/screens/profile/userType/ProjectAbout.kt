package com.example.kilt.screens.profile.userType

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.core.navigation.NavPath

@Composable
fun ProjectAbout(navController : NavHostController){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "О проекте",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 24.sp
        )
        SettingItem(
            icon = null,
            title = "Партнеры",
            ""
        ) { navController.navigate(NavPath.BLOGPAGE.name) }
        SettingItem(
            icon = null,
            title = "О приложении",
            url = "",
            onClick = {navController.navigate(NavPath.BLOGPAGE.name) }
        )
        SettingItem(
            icon = null,
            title = "Политика конфиденциальности",
            url = "https://kiltapp.kz/uploads/privacy.pdf",
            onClick = {navController.navigate(NavPath.BLOGPAGE.name) }
        )
        SettingItem(
            icon = null,
            title = "Пользовательское соглашение",
            url = "https://kiltapp.kz/uploads/user_agreement.pdf",
            onClick = {navController.navigate(NavPath.BLOGPAGE.name) }
        )
        SettingItem(
            icon = null,
            title = "Связаться с нами",
            ""
        ) { navController.navigate(NavPath.BLOGPAGE.name) }
        SettingItem(
            icon = null,
            title = "Правила",
            "https://kiltapp.kz/uploads/rules.pdf"
        ) { navController.navigate(NavPath.BLOGPAGE.name) }
    }
}
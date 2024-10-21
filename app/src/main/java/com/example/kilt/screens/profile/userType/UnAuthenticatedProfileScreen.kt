package com.example.kilt.screens.profile.userType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.navigation.NavPath
import com.example.kilt.screens.profile.login.LoginButton
import com.example.kilt.screens.profile.registration.RegistrationButton


@Composable
fun UnAuthenticatedProfileScreen(navController: NavHostController){
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
        LoginButton(navController, Modifier.padding(horizontal = 8.dp), onClick = { navController.navigate(NavPath.LOGIN.name) })
        Spacer(modifier = Modifier.height(12.dp))
        RegistrationButton(navController, Modifier.padding(horizontal = 8.dp),onClick = { navController.navigate(NavPath.REGISTRATIONPAGE.name)})
    }
    Spacer(modifier = Modifier.height(24.dp))
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.document_icon),
            title = "Блог",
            url = "",
            onClick = {navController.navigate(NavPath.BLOGPAGE.name) }
        )
        ProjectAbout(navController = navController)
    }
}
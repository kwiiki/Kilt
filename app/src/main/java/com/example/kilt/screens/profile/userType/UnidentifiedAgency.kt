package com.example.kilt.screens.profile.userType

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.data.authentification.User
import com.example.kilt.navigation.NavPath
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun UnidentifiedAgency(authViewModel: AuthViewModel, user: User,navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
                .height(113.dp)
                .padding(16.dp)
                .clickable { navController.navigate(NavPath.IDENTIFICATIONSCREEN.name) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.succesful_image),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Color.Transparent, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Пройти идентификацию",
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                lineHeight = 24.sp,
                color = Color(0xff010101),
                modifier = Modifier.width(160.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF566982),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        UserMenu(navController = navController)
        Text(text = "Выйти",
            fontSize = 18.sp,
            color = Color.Red,
            modifier = Modifier.clickable { authViewModel.logout() }
        )
    }
}
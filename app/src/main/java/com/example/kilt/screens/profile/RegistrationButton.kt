package com.example.kilt.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kilt.navigation.NavPath


@Composable
fun RegistrationButton(navController: NavController,modifier: Modifier){
    Row(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = {navController.navigate(NavPath.REGISTRATIONPAGE.name)},
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 1.dp, color = Color(0xff3244E4)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Transparent, RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Регистрация",
                        color = Color(0xff3244E4),
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
package com.example.kilt.screens.searchpage.chooseCity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.viewmodels.ChooseCityViewModel


@Composable
fun TopAppBarChooseCityPage(
    chooseCityViewModel: ChooseCityViewModel,
    navController: NavHostController,
    currentScreen: Int,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            if (currentScreen == 0) {
                navController.popBackStack()
            } else {
                chooseCityViewModel.goBack()
            }
        }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xff566982)
            )
        }
        TextButton(onClick = {
            chooseCityViewModel.resetSelection()
        }) {
            Text(
                text = "Сбросить",
                fontSize = 16.sp,
                color = Color(0xff566982),
                fontWeight = FontWeight.W400
            )
        }
    }
}
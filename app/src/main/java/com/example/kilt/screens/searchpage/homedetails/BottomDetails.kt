package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kilt.screens.searchpage.GradientButton
import com.example.kilt.screens.searchpage.WhatsAppGradientButton
import com.example.kilt.screens.searchpage.gradient

@Composable
fun BottomDetails() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary,
                windowInsets = WindowInsets(bottom = 0, top = 0)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GradientButton(
                        text = "Позвонить",
                        textColor = Color.White,
                        gradient = gradient,
                        onClick = { /* TODO: Handle click */ },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    WhatsAppGradientButton(
                        text = "Написать",
                        onClick = { /* TODO: Handle click */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Черная полоса под NavigationBar
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Black)
            )
        }
    }
}
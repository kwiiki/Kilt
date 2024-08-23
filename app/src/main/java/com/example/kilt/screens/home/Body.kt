package com.example.kilt.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.R


@Composable
fun Body(modifier: Modifier) {
    val font = FontFamily(Font(R.font.lato_bold))
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color(0xffF5F5F5), shape = RoundedCornerShape(15.dp))
            .border(0.dp, Color(0xFFFFFFFF), RoundedCornerShape(15.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F5F5), shape = RoundedCornerShape(15.dp)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(2.dp, Color.Blue, RoundedCornerShape(15.dp))
                    .background(Color.White, RoundedCornerShape(15.dp))
                    .padding(horizontal = 25.dp)
            ) {
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                ) {
                    Text(
                        "Арендовать",
                        color = Color(0xff110D28),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xffF5F5F5), RoundedCornerShape(15.dp))

            ) {
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .align(alignment = Alignment.Center)
                ) {
                    Text("Купить", fontFamily = font, color = Color(0xff110D28), fontSize = 16.sp)
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewBody() {
    Body(Modifier.padding(horizontal = 16.dp))
}
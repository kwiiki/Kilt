package com.example.kilt.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.R
import com.example.kilt.custom.CustomToggleButton


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
fun ButtonRow(modifier: Modifier) {
    var isRentSelected by remember { mutableStateOf(true) }
    var isBuySelected by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Color(0xffF2F2F2))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomToggleButton1(
            text = "Арендовать",
            isSelected = isRentSelected,
            onClick = {
                isRentSelected = true
                isBuySelected = false
            },
            modifier = Modifier.weight(1f)
        )
        CustomToggleButton1(
            text = "Купить",
            isSelected = isBuySelected,
            onClick = {
                isRentSelected = false
                isBuySelected = true
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CustomToggleButton1(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF2F2F2), Color(0xFFF2F2F2)),
        startY = 0f,
        endY = 300f
    )
    val backgroundBrush = if (isSelected) Brush.verticalGradient(
        colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
    ) else gradient
    val textColor = if (isSelected) Color(0xffFFFFFF) else Color(0xff566982)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.White, RoundedCornerShape(1.5.dp))
            .border(2.dp, Color.Blue, RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = text,
            color = Color.Black,
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewBottomRow(){
    ButtonRow(modifier = Modifier)
}

@Composable
@Preview(showBackground = true)
fun PreviewBody() {
    Body(Modifier.padding(horizontal = 16.dp))
}
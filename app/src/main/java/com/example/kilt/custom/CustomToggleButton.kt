package com.example.kilt.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomToggleButton(
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
            .height(51.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundBrush)
            .clickable { onClick() }
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = text,
            color = textColor,
        )
    }
}

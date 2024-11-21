package com.example.kilt.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.kilt.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.lato_bold)
        ),
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.lato_bold)
        ),
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.lato_bold)
        ),
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.lato_bold)
        ),
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val LatoWeight600 = FontFamily(
    Font(R.font.lato_bold)
)
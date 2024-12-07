package com.example.kilt.presentation.editprofile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kilt.R

@Composable
fun CustomButtonForEdit(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorList: List<Color>,
    colorBrush: Brush
) {
    val tintColor = if (text == "Удалить аккаунт") Color(0xFFE63312) else Color(0xFF1B278F)
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF326be4), Color(0xFF1B308F)),
        startX = 100f,
        endX = 700f
    )
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = colorBrush,
                shape = RoundedCornerShape(12.dp)
            )
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (text) {
                    "Выбрать фото" -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.gallerya_icon),
                            contentDescription = null,
                            tint = Color(0xFF1B278F)
                        )
                    }
                    "Сохранить" -> {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = tintColor
                        )
                    }
                    else -> {
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = text,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = colorList,
                            tileMode = TileMode.Mirror
                        ),
                    ), fontWeight = FontWeight.W700
                )
            }
        }
    }
}

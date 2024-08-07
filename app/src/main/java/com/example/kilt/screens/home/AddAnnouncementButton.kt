package com.example.kilt.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun AddAnnouncementButton(modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = {},
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Blue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().
            height(50.dp)
        ) {


            Icon(
                Icons.Default.Add,
                contentDescription = "content description",
                tint = Color(0XFFFFFFFF)
            )
            Text(text = "Добавить обьявление", color = Color.White)
        }
    }
}
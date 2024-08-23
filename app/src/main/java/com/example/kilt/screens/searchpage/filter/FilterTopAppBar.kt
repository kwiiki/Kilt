package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterTopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick ={
            }, modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                Icons.Default.Close, contentDescription = "Close",
                tint = Color(0xff566982),
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            "Фильтр",
            color = Color(0xff010101),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 35.dp),
            textAlign = TextAlign.Center
        )
        TextButton(
            onClick = {
            },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(
                "Стереть",
                color = Color(0xff566982),
                fontWeight = FontWeight.W400,
                fontSize = 16.sp
            )
        }
    }
}
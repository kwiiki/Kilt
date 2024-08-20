package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LocationSection(modifier: Modifier) {
    Column(modifier = modifier.padding()) {
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle location input */ },
            label = { Text("Город, район, ЖК", color = Color(0xff566982)) },
            trailingIcon = {
                IconButton(onClick = { /* Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
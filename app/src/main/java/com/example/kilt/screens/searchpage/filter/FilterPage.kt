@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.custom.CustomToggleButton


@Preview(showBackground = true)
@Composable
fun PreviewFilterPage() {
    FilterPage()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterPage() {
    Scaffold(
        topBar = { FilterTopAppBar() },
        content = { FilterContent() },
        bottomBar = { ShowResultsButton() }
    )
}

@Composable
fun FilterTopAppBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Фильтр",
                    color = Color(0xff010101),
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    Icons.Default.Close, contentDescription = "Close",
                    tint = Color(0xff566982)
                )
            }
        },
        actions = {
            TextButton(onClick = { /* Handle reset action */ }) {
                Text(
                    "Стереть",
                    color = Color(0xff566982),
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FilterContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(85.dp))
        // Rent or Buy Toggle
        ButtonRow()
        LocationSection()
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color(0xffF2F2F2)))
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Property Type Section
        PropertyTypeSection()
//
//        // Add other sections like Number of Rooms, Price, Year Built, etc.
//
//        // Final Bottom Padding to avoid overlap with bottom bar
//        Spacer(modifier = Modifier.height(72.dp))
//    }
    }
}

@Composable
fun ButtonRow() {
    var isRentSelected by remember { mutableStateOf(true) }
    var isBuySelected by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(Color(0xffF2F2F2))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomToggleButton(
            text = "Арендовать",
            isSelected = isRentSelected,
            onClick = {
                isRentSelected = true
                isBuySelected = false
            },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(2.dp))

        CustomToggleButton(
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
fun LocationSection() {
    Column {
        Text(
            "Где размещен",
            color = Color(0xff010101),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle location input */ },
            label = { Text("Город, район, ЖК", color = Color(0xff566982)) },
            trailingIcon = {
                IconButton(onClick = { /* Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PropertyTypeSection() {
    Column {
        Text("Тип жилья")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(
                onClick = { /* Handle residential selection */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text("Жилая", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Handle commercial selection */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text("Коммерческая")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PropertyTypeButton("Квартира")
            PropertyTypeButton("Дом")
            PropertyTypeButton("Участок")
        }
    }
}

@Composable
fun PropertyTypeButton(type: String) {
    Button(
        onClick = { /* Handle property type selection */ },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        )
    ) {
        Text(type)
    }
}

@Composable
fun ShowResultsButton() {
    Button(
        onClick = { /* Handle showing results */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Показать 312 вариантов")
    }
}
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kilt.custom.CustomToggleButton


@Preview(showBackground = true)
@Composable
fun PreviewFilterPage() {
    FilterPage()
}

@Composable
fun FilterPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FilterTopAppBar()
        FilterContent()
    }
}

@Composable
fun FilterContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtonRow(Modifier.padding(16.dp))
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        TypeOfHousing(Modifier.padding(horizontal = 8.dp))
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        LocationSection(Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        OnlyOwnersSection()
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
    }
}
@Composable
fun ButtonRow(modifier: Modifier) {
    var isRentSelected by remember { mutableStateOf(true) }
    var isBuySelected by remember { mutableStateOf(false) }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(15.dp))
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



@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.quickFilters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun QuickRangeFilter(
    prop: String,
    searchViewModel: SearchViewModel,
    onApplyClick: () -> Unit,
    title: String
) {
    val (initialMin, initialMax) = searchViewModel.getRangeFilterValues(prop)
    var minValue by remember { mutableStateOf(if (initialMin > 0) initialMin.toString() else "") }
    var maxValue by remember { mutableStateOf(if (initialMax < Int.MAX_VALUE && initialMax > 0) initialMax.toString() else "") }


    val trailingText = when (prop) {
        "price" -> "тг."
        "area", "living_area", "land_area", "kitchen_area" -> "м²"
        else -> ""
    }
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Spacer(
            modifier = Modifier
                .padding(start = 150.dp)
                .padding(bottom = 8.dp) // Add some padding if needed
                .height(5.dp)
                .width(52.dp)
                .background(Color(0xffDBDFE4), RoundedCornerShape(12.dp))
        )
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = minValue,
                onValueChange = { newValue ->
                    minValue = newValue.filter { it.isDigit() }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFcfcfcf),
                    focusedBorderColor = Color(0xFFcfcfcf),
                    cursorColor = Color.Black
                ),
                trailingIcon = {
                    if (trailingText.isNotEmpty()) {
                        Text(
                            text = trailingText,
                            fontWeight = FontWeight.W400,
                            color = Color(0xff010101)
                        )
                    }
                }
            )
            Text(text = "до", color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
            OutlinedTextField(
                value = maxValue,
                onValueChange = { newValue ->
                    maxValue = newValue.filter { it.isDigit() }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFcfcfcf),
                    focusedBorderColor = Color(0xFFcfcfcf),
                    cursorColor = Color.Black
                ),
                trailingIcon = {
                    if (trailingText.isNotEmpty()) {
                        Text(
                            text = trailingText,
                            fontWeight = FontWeight.W400,
                            color = Color(0xff010101)
                        )
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp)) // Ensure space between rows
        Button(
            onClick = {
                searchViewModel.performSearch()
                onApplyClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(
                text = "Применить",
                color = Color.White,
            )
        }

    }
    LaunchedEffect(minValue, maxValue) {
        val min = minValue.toIntOrNull() ?: 0
        val max = maxValue.toIntOrNull() ?: 0
        searchViewModel.updateRangeFilter(prop, min, max)
    }
}


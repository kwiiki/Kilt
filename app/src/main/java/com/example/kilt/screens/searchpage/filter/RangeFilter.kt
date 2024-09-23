@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun RangeFilter(
    prop: String,
    title: String,
    searchViewModel: SearchViewModel,
    onFocusChanged: (Boolean) -> Unit,
    isFocused: Boolean
) {
    val (initialMin, initialMax) = searchViewModel.getRangeFilterValues(prop)
    var minValue by remember { mutableStateOf(if (initialMin > 0) initialMin.toString() else "") }
    var maxValue by remember { mutableStateOf(if (initialMax < Int.MAX_VALUE) initialMax.toString() else "") }

    val focusManager = LocalFocusManager.current

    val trailingText = when (prop) {
        "price" -> "тг."
        "area", "living_area", "land_area", "kitchen_area" -> "м²"
        else -> ""
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = minValue,
                onValueChange = {
                    minValue = it.filter { it.isDigit() }
                    if (it.isEmpty()) {
                        searchViewModel.updateRangeFilter(prop, 0, maxValue.toIntOrNull() ?: Int.MAX_VALUE)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFcfcfcf),
                    focusedBorderColor = Color(0xFFcfcfcf),
                    cursorColor = if (isFocused) LocalContentColor.current else Color.Transparent
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
            Text(text = "до", modifier = Modifier.padding(horizontal = 8.dp))
            OutlinedTextField(
                value = maxValue,
                onValueChange = {
                    maxValue = it.filter { it.isDigit() }
                    if (it.isEmpty()) {
                        searchViewModel.updateRangeFilter(prop, minValue.toIntOrNull() ?: 0, Int.MAX_VALUE)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFcfcfcf),
                    focusedBorderColor = Color(0xFFcfcfcf),
                    cursorColor = if (isFocused) LocalContentColor.current else Color.Transparent
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
    }
    CustomDivider()
    LaunchedEffect(minValue, maxValue) {
        val min = minValue.toIntOrNull() ?: 0
        val max = maxValue.toIntOrNull() ?: Int.MAX_VALUE
        searchViewModel.updateRangeFilter(prop, min, max)
    }
    val filters by searchViewModel.filters.collectAsState()
    LaunchedEffect(filters) {
        val (min, max) = searchViewModel.getRangeFilterValues(prop)
        minValue = if (min > 0) min.toString() else ""
        maxValue = if (max < Int.MAX_VALUE) max.toString() else ""
    }
}
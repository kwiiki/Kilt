@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.presentation.search.FiltersViewModel

@Composable
fun RangeFilter(
    prop: String,
    title: String,
    filtersViewModel: FiltersViewModel, // Используем FiltersViewModel
    onFocusChanged: (Boolean) -> Unit,
) {
    val filtersState by filtersViewModel.filtersState.collectAsState()
    val (initialMin, initialMax) = filtersViewModel.getRangeFilterValues(prop)
    var minValue by remember { mutableStateOf(if (initialMin > 0) initialMin.toString() else "") }
    var maxValue by remember { mutableStateOf(if (initialMax < Long.MAX_VALUE && initialMax > 0) initialMax.toString() else "") }

    val focusManager = LocalFocusManager.current
    val trailingText = when (prop) {
        "price" -> "тг."
        "area", "living_area", "land_area", "kitchen_area" -> "м²"
        else -> ""
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = { focusManager.clearFocus() })
    ) {
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
            NumberTextField(
                value = minValue,
                onValueChange = { newValue ->
                    minValue = newValue
                    val min = newValue.toLongOrNull() ?: 0
                    val max = maxValue.replace(" ", "").toLongOrNull() ?: Long.MAX_VALUE
                    filtersViewModel.updateRangeFilter(prop, min, max) // Обновляем через FiltersViewModel
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                trailingText = trailingText,
                focusManager = focusManager
            )
            Text(text = "до", modifier = Modifier.padding(horizontal = 8.dp))
            NumberTextField(
                value = maxValue,
                onValueChange = { newValue ->
                    maxValue = newValue
                    val min = minValue.replace(" ", "").toLongOrNull() ?: 0
                    val max = newValue.toLongOrNull() ?: Long.MAX_VALUE
                    filtersViewModel.updateRangeFilter(prop, min, max) // Обновляем через FiltersViewModel
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                trailingText = trailingText,
                focusManager = focusManager
            )
        }
    }
    CustomDivider()

    // Следим за изменениями фильтров
    LaunchedEffect(filtersState) {
        val (min, max) = filtersViewModel.getRangeFilterValues(prop)
        minValue = if (min > 0) min.toString() else ""
        maxValue = if (max < Long.MAX_VALUE) max.toString() else ""
    }
}

@Composable
fun NumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingText: String,
    focusManager: FocusManager
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = formatNumber(value), selection = TextRange(formatNumber(value).length))) }

    OutlinedTextField(
        value = textFieldValueState,
        onValueChange = { newValue ->
            val oldText = textFieldValueState.text
            val newText = newValue.text
            val oldSelection = textFieldValueState.selection.start
            val newSelection = newValue.selection.start
            val unformattedNewText = newText.replace(" ", "")
            val formattedNewText = formatNumber(unformattedNewText)
            val oldFormattedCursorPosition = oldText.take(oldSelection).count { it.isDigit() }
            val newUnformattedCursorPosition = newText.take(newSelection).count { it.isDigit() }

            val cursorOffset = newUnformattedCursorPosition - oldFormattedCursorPosition
            val newCursorPosition = formattedNewText.mapIndexed { index, c ->
                if (c.isDigit()) index else -1
            }.filter { it != -1 }.getOrNull(oldFormattedCursorPosition + cursorOffset) ?: formattedNewText.length

            textFieldValueState = TextFieldValue(
                text = formattedNewText,
                selection = TextRange(newCursorPosition)
            )

            onValueChange(unformattedNewText)
        },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFcfcfcf),
            focusedBorderColor = Color(0xFFcfcfcf),
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
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

fun formatNumber(number: String): String {
    return number.reversed().chunked(3).joinToString(" ").reversed()
}

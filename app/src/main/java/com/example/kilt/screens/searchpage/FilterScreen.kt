package com.example.kilt.screens.searchpage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterButtons(
    filters: List<String>,
    onFilterSelected: (String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(filters) { filter ->
            FilterButton(
                text = filter,
                isSelected = filter == selectedFilter,
                onClick = {
                    selectedFilter = if (selectedFilter == filter) null else filter
                    onFilterSelected(selectedFilter ?: "")
                }
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isClicked by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .height(36.dp)
            .fillMaxWidth()
            .border(width = 1.dp, Color(0xffc2c2d6), RoundedCornerShape(16.dp))
            .clickable(
                enabled = !isClicked,
                onClick = {
                    isClicked = true
                    onClick()
                }
            )
            .background(
                color = Color(0xffF2F2F2),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xff110D28),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun FilterScreen() {
    val filters = listOf("Купить", "Квартира", "Количество комнат", "Цена", "Площадь")

    FilterButtons(
        filters = filters,
        onFilterSelected = { selectedFilter ->
            // Обработка выбранного фильтра
            println("Выбран фильтр: $selectedFilter")
        }
    )
}

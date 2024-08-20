package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.data.NumRoom
import com.example.kilt.viewmodels.HomeSaleViewModel

@Composable
fun ChooseNumRoom(homeSaleViewModel: HomeSaleViewModel) {
    homeSaleViewModel.loadHomeSale()
    val filters = homeSaleViewModel.config.value?.propMapping?.num_rooms?.list
    NumberRoomFilterButtons(
        filters = filters,
        onFilterSelected = { selectedFilters ->
            println("Выбраны фильтры: $selectedFilters")
        }
    )
}

@Composable
fun NumberRoomFilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .widthIn(35.dp)// Увеличенная минимальная ширина
            .wrapContentWidth() // Позволяет расширяться при необходимости
            .border(
                width = 1.5.dp,
                color = if (isSelected) Color.Blue else Color(0xffc2c2d6),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Color.Blue.copy(alpha = 0.1f) else Color(0xffFFFFFF),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (isSelected) Color.Blue else Color(0xff110D28),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .widthIn(35.dp)
                .align(alignment = Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NumberRoomFilterButtons(
    filters: List<NumRoom>?,
    onFilterSelected: (List<Int>) -> Unit
) {
    var selectedFilters by remember { mutableStateOf<List<Int>>(emptyList()) }

    Column(modifier = Modifier.padding(horizontal = 16.dp).padding(top = 8.dp)) {
        Text(text = "Количество комнат",
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            color = Color(0xff010101)
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            NumberRoomFilterButton(
                text = "Все",
                isSelected = selectedFilters.isEmpty(),
                onClick = {
                    selectedFilters = emptyList()
                    onFilterSelected(selectedFilters)
                }
            )
        }
        items(filters ?: emptyList()) { filter ->
            NumberRoomFilterButton(
                text = filter.name.toString(),
                isSelected = selectedFilters.contains(filter.id),
                onClick = {
                    selectedFilters = if (selectedFilters.contains(filter.id)) {
                        selectedFilters - filter.id
                    } else {
                        selectedFilters + filter.id
                    }
                    onFilterSelected(selectedFilters)
                }
            )
        }
    }
}

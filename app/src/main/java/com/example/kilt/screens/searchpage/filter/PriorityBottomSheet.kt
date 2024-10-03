package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun PriorityBottomSheet(
    searchViewModel: SearchViewModel,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf(
        "Популярные" to "default",
        "Новые предложения" to "new",
        "Цена по возрастанию" to "price_asc",
        "Цена по убыванию" to "price_desc"
    )
    Column(
        modifier = Modifier
            .height(330.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Сортировка",
            fontSize = 22.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        options.forEach { (optionText, sortValue) ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        onOptionSelected(optionText)
                        searchViewModel.updateSort(sortValue)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = optionText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
                if (selectedOption == optionText) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "CheckIcon"
                    )
                }
            }
        }
    }
}
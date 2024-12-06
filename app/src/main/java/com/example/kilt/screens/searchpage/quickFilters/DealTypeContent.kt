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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.models.FilterValue
import com.example.kilt.presentation.custom.CustomToggleButton
import com.example.kilt.presentation.search.FiltersViewModel
import com.example.kilt.presentation.search.SearchResultsViewModel


@Composable
fun DealTypeContent(
    filtersViewModel: FiltersViewModel,
    searchResultsViewModel: SearchResultsViewModel,
    onApplyClick: () -> Unit
) {
    val filtersState by filtersViewModel.filtersState.collectAsState()
    val sorting by filtersViewModel.sorting.collectAsState()

    val dealType = (filtersState.filters["deal_type"] as? FilterValue.SingleValue)?.value ?: 1
    val isRentSelected = dealType == 1
    val isBuySelected = dealType == 2


    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Spacer(
            modifier = Modifier
                .padding(start = 150.dp)
                .padding(bottom = 8.dp)
                .height(5.dp)
                .width(52.dp)
                .background(Color(0xffDBDFE4), RoundedCornerShape(12.dp))
        )
        Text(
            "Тип сделки",
            fontSize = 22.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(Color(0xffF2F2F2))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomToggleButton(
                text = "Арендовать",
                isSelected = isRentSelected,
                onClick = {
                    filtersViewModel.updateFilter("deal_type", FilterValue.SingleValue(1))
                },
                modifier = Modifier.weight(1f)
            )
            CustomToggleButton(
                text = "Купить",
                isSelected = isBuySelected,
                onClick = {
                    filtersViewModel.updateFilter("deal_type", FilterValue.SingleValue(2))
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                searchResultsViewModel.updateFiltersAndPerformSearch(filtersState, sorting)
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
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

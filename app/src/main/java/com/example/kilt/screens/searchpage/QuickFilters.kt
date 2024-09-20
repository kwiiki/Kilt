package com.example.kilt.screens.searchpage

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.kilt.R
import com.example.kilt.enums.TypeFilters
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun FilterButtons(
    filters: List<String>,
    onFilterButtonClicked: (Boolean) -> Unit,
    onFilterSelected: (String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xffF2F2F2), shape = (RoundedCornerShape(16.dp)))
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(alignment = Alignment.Center)
                        .clickable { onFilterButtonClicked(true) }
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color(0xffc2c2d6), RoundedCornerShape(12.dp))
                    .background(
                        color = Color(0xffF2F2F2),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Купить",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xff110D28),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

        }
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
            .border(width = 1.dp, Color(0xffc2c2d6), RoundedCornerShape(12.dp))
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
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xff110D28),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickFilters(
    configViewModel: ConfigViewModel,
    searchViewModel: SearchViewModel,
    onFilterButtonClicked: (Boolean) -> Unit
) {
    val advancedConfig = configViewModel.config.value?.advanced
    val propLabels = configViewModel.config.value?.propLabels
    val propertyTypes = configViewModel.config.value?.propertyTypes

    val currentPropertyType = propertyTypes?.find { it.id == searchViewModel.propertyType.value }
    val currentListingType = currentPropertyType?.listing_type ?: searchViewModel.listingType.value

    val filters = when {
        searchViewModel.dealType.value == 1 && currentPropertyType?.id == 1 && currentListingType == 1 ->
            advancedConfig?.quick_filters_1_1_1
        searchViewModel.dealType.value == 2 && currentPropertyType?.id == 1 && currentListingType == 1 ->
            advancedConfig?.quick_filters_1_1_2
        searchViewModel.dealType.value == 1 && currentPropertyType?.id == 2 && currentListingType == 1 ->
            advancedConfig?.quick_filters_2_1_1
        searchViewModel.dealType.value == 2 && currentPropertyType?.id == 2 && currentListingType == 1 ->
            advancedConfig?.quick_filters_2_1_2
        searchViewModel.dealType.value == 1 && currentPropertyType?.id == 6 && currentListingType == 2 ->
            advancedConfig?.quick_filters_1_2_6
        else ->
            advancedConfig?.quick_filters_2_2_6
    }?.split(",")?.map { it.trim() } ?: emptyList()

    Log.d("quickFilters", "QuickFilters: $filters")

    val filterLabels = filters.map { filterProperty ->
        when (filterProperty) {
            TypeFilters.PROPERTY_TYPE.value -> currentPropertyType?.label_ru ?: "Тип недвижимости"
            else -> propLabels?.find { it.property == filterProperty }?.label_ru ?: filterProperty
        }
    }

    FilterButtons(
        filters = filterLabels,
        onFilterButtonClicked = onFilterButtonClicked,
        onFilterSelected = { selectedFilter ->
            println("Выбран фильтр: $selectedFilter")
        }
    )
}
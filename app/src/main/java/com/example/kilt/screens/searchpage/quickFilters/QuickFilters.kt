@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.quickFilters

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.kilt.models.FilterValue
import com.example.kilt.presentation.search.FiltersViewModel
import com.example.kilt.presentation.search.SearchResultsViewModel
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FilterButtons(
    configViewModel: ConfigViewModel,
    filterEng: List<String>,
    filtersViewModel: FiltersViewModel,
    searchResultsViewModel: SearchResultsViewModel,
    filters: List<String>,
    onFilterButtonClicked: (Boolean) -> Unit
) {
    var selectedFilterEng by remember { mutableStateOf<String?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isDealTypeClicked by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    val currentFilters = filtersViewModel.filtersState.value.filters
    val filterMap = filterEng.zip(filters).toMap()

    val propertyType = filtersViewModel.getPropertyType()
    val propertyTypeText = when (propertyType) {
        1 -> "Квартира"
        2 -> "Дом"
        else -> "Тип недвижимости"
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            FilterIconButton(onClick = { onFilterButtonClicked(true) })
        }
        item {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = Color.Blue.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        showBottomSheet = true
                        isDealTypeClicked = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (filtersViewModel.getDealType() == 1) "Арендовать" else "Купить",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        items(filters.zip(filterEng)) { (filter, filterEngValue) ->
            val isActive = currentFilters[filterEngValue]?.let {
                when (it) {
                    is FilterValue.RangeValue -> it.from > 0 || it.to > 0
                    is FilterValue.ListValue -> it.values.isNotEmpty()
                    is FilterValue.SingleValue -> it.value != 0
                    else -> false
                }
            } ?: false
            val displayText = if (filterEngValue == "property_type") propertyTypeText else filter
            val isSelected =
                selectedFilterEng == filterEngValue || filterEngValue == "property_type" // Force property_type selected
            FilterButton(
                text = displayText,
                filterEngValue = filterEngValue,
                isActive = isActive,
                isSelected = isSelected,
                onClick = {
                    selectedFilterEng = filterEngValue
                    showBottomSheet = true
                }
            )
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                isDealTypeClicked = false
                selectedFilterEng = null
            },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(16.dp),
            dragHandle = {},
            contentColor = Color.White,
            containerColor = Color.White
        ) {
            BottomSheetContent(
                selectedFilterEng = selectedFilterEng,
                isDealTypeClicked = isDealTypeClicked,
                configViewModel = configViewModel,
                filtersViewModel = filtersViewModel,
                searchResultsViewModel = searchResultsViewModel,
                filterMap = filterMap,
                onClose = { showBottomSheet = false }
            )
        }
    }
}


@Composable
fun BottomSheetContent(
    selectedFilterEng: String?,
    isDealTypeClicked: Boolean,
    configViewModel: ConfigViewModel,
    filtersViewModel: FiltersViewModel,
    searchResultsViewModel: SearchResultsViewModel,
    filterMap: Map<String, String>,
    onClose: () -> Unit
) {
    when {
        isDealTypeClicked -> {
            DealTypeContent(filtersViewModel, searchResultsViewModel) {
                onClose()
            }
        }

        selectedFilterEng == "property_type" -> HouseOrFlat(
            filtersViewModel = filtersViewModel,
            searchResultsViewModel = searchResultsViewModel,
            selectedFilterEng,
            title = filterMap[selectedFilterEng] ?: "",
            onApplyClick = {
                onClose()
            }
        )

        selectedFilterEng == "num_rooms" -> ListQuickFilter(
            searchResultsViewModel = searchResultsViewModel,
            configViewModel = configViewModel,
            filtersViewModel = filtersViewModel,
            prop = selectedFilterEng,
            title = filterMap[selectedFilterEng] ?: "",
            onApplyClick = { onClose() }
        )

        selectedFilterEng in listOf("price", "area") -> QuickRangeFilter(
            searchResultsViewModel = searchResultsViewModel,
            prop = selectedFilterEng!!,
            filtersViewModel = filtersViewModel,
            title = filterMap[selectedFilterEng] ?: "",
            onApplyClick = { onClose() }
        )

        else -> {
            Text("Выберите фильтр")
        }
    }
}

@Composable
fun FilterIconButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color(0xffF2F2F2), shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.filter_icon),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.Center)
        )
    }
}


@Composable
fun FilterButton(
    text: String,
    filterEngValue: String,
    isSelected: Boolean,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val buttonColor = when {
        isActive -> Color.Blue
        isSelected -> Color.Blue
        else -> Color(0xffc2c2d6)
    }

    val backgroundColor = when {
        isActive -> Color.Blue.copy(alpha = 0.05f)
        isSelected -> Color.Blue.copy(alpha = 0.05f)
        else -> Color(0xffF2F2F2)
    }

    val textColor = when {
        isActive -> Color.Blue
        isSelected -> Color.Blue
        else -> Color(0xff110D28)
    }

    Box(
        modifier = Modifier
            .height(40.dp)
            .wrapContentWidth()
            .border(
                width = 1.dp,
                color = buttonColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (filterEngValue == "new_building") "Новостройки" else text,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickFilters(
    configViewModel: ConfigViewModel,
    searchResultViewModel: SearchResultsViewModel,
    filtersViewModel: FiltersViewModel,
    onFilterButtonClicked: (Boolean) -> Unit
) {
    val filtersState by filtersViewModel.filtersState.collectAsState()
    val advancedConfig = configViewModel.config.value?.advanced
    val propLabels = configViewModel.config.value?.propLabels
    val dealType = filtersViewModel.getDealType()
    val listingType = filtersViewModel.getListingType()
    val propertyType = filtersViewModel.getPropertyType()

    val filters = when {
        dealType == 1 && propertyType == 1 && listingType == 1 ->
            advancedConfig?.quick_filters_1_1_1

        dealType == 1 && propertyType == 2 && listingType == 1 ->
            advancedConfig?.quick_filters_1_1_2

        dealType == 2 && propertyType == 1 && listingType == 1 ->
            advancedConfig?.quick_filters_2_1_1

        dealType == 2 && propertyType == 2 && listingType == 1 ->
            advancedConfig?.quick_filters_2_1_2

        dealType == 1 && propertyType == 6 && listingType == 2 ->
            advancedConfig?.quick_filters_1_2_6

        else ->
            advancedConfig?.quick_filters_2_2_6
    }?.split(",")?.map { it.trim() } ?: emptyList()

    val filterLabels = filters.map { filterProperty ->
        propLabels?.find { it.property == filterProperty }?.label_ru ?: filterProperty
    }

    FilterButtons(
        configViewModel = configViewModel,
        filterEng = filters,
        filtersViewModel = filtersViewModel,
        searchResultsViewModel = searchResultViewModel,
        filters = filterLabels,
        onFilterButtonClicked = onFilterButtonClicked
    )
}
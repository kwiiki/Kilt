@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.quickFilters

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.kilt.models.Filters
import com.example.kilt.utills.enums.TypeFilters
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun FilterButtons(
    configViewModel: ConfigViewModel,
    filterEng: List<String>,
    searchViewModel: SearchViewModel,
    filters: List<String>,
    onFilterButtonClicked: (Boolean) -> Unit,
    onFilterSelected: (String) -> Unit,
    currentFilters: Filters
) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedFilterEng by remember { mutableStateOf<String?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isDealTypeClicked by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    fun resetSelectedFilter() {
        selectedFilter = null
        selectedFilterEng = null
    }

    val filterMap = filterEng.zip(filters).toMap()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xffF2F2F2), shape = RoundedCornerShape(16.dp))
                    .clickable {
                        onFilterButtonClicked(true)
                    }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(alignment = Alignment.Center)
                )
            }
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
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        showBottomSheet = true
                        isDealTypeClicked = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchViewModel.dealType.value == 1) "Арендовать" else "Купить",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        items(filters.zip(filterEng)) { (filter, filterEngValue) ->
            Log.d("filter123", "FilterButtons: ${currentFilters.filterMap[filterEngValue]}")
            val isActive = currentFilters.filterMap[filterEngValue]?.let {
                when (it) {
                    is FilterValue.RangeValue -> {
                        it.from > 0 || it.to > 0
                    }
                    is FilterValue.ListValue1 -> it.values.isNotEmpty()
                    is FilterValue.SingleValue -> it.value != 0
                    is FilterValue.ListValue -> it.values.isNotEmpty()
                }
            } ?: false

            FilterButton(
                text = filter,
                filterEngValue = filterEngValue,
                isSelected = showBottomSheet && filterEngValue == selectedFilterEng,
                isActive = isActive,
                onClick = {
                    selectedFilterEng = filterEngValue
                    showBottomSheet = true
                    onFilterSelected(filter)
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
            contentColor = Color.White,
            containerColor = Color.White,
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Log.d("selectedFilterEng", "FilterButtons: $selectedFilterEng")
                when {
                    isDealTypeClicked -> {
                        DealTypeContent(searchViewModel) {
                            showBottomSheet = false
                            isDealTypeClicked = false
                        }
                    }
                    selectedFilterEng == "num_rooms" -> ListQuickFilter(
                        configViewModel = configViewModel,
                        searchViewModel = searchViewModel,
                        prop = selectedFilterEng!!,
                        title = filterMap[selectedFilterEng] ?: selectedFilter!!,
                        onApplyClick = {
                            showBottomSheet = false
                        }
                    )
                    selectedFilterEng in listOf("property_type") -> HouseOrFlat(
                        searchViewModel,
                        selectedFilterEng!!,
                        title = filterMap[selectedFilterEng] ?: selectedFilter!!,
                        onApplyClick = {
                            showBottomSheet = false
                        }
                    )
                    selectedFilterEng in listOf("price", "area") -> QuickRangeFilter(
                        selectedFilterEng!!,
                        searchViewModel,
                        title = filterMap[selectedFilterEng] ?: selectedFilter!!,
                        onApplyClick = {
                            showBottomSheet = false
                            resetSelectedFilter()
                        }
                    )
                    else -> {
                        Text("Выберите фильтр")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
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
    searchViewModel: SearchViewModel,
    onFilterButtonClicked: (Boolean) -> Unit
) {
    val currentFilters by searchViewModel.filters.collectAsState()
    val advancedConfig = configViewModel.config.value?.advanced
    val propLabels = configViewModel.config.value?.propLabels
    val propertyTypes = configViewModel.config.value?.propertyTypes
    val currentPropertyType = propertyTypes?.find { it.id == searchViewModel.propertyType.value }
    val currentListingType = currentPropertyType?.listing_type ?: searchViewModel.listingType.value
    val filters = when {
        searchViewModel.dealType.value == 1 && currentPropertyType?.id == 1 && currentListingType == 1 ->
            advancedConfig?.quick_filters_1_1_1

        searchViewModel.dealType.value == 1 && currentPropertyType?.id == 2 && currentListingType == 1 ->
            advancedConfig?.quick_filters_1_1_2

        searchViewModel.dealType.value == 2 && currentPropertyType?.id == 1 && currentListingType == 1 ->
            advancedConfig?.quick_filters_2_1_1

        searchViewModel.dealType.value == 2 && currentPropertyType?.id == 2 && currentListingType == 1 ->
            advancedConfig?.quick_filters_2_1_2

        searchViewModel.dealType.value == 1 && currentPropertyType?.id == 6 && currentListingType == 2 ->
            advancedConfig?.quick_filters_1_2_6

        else ->
            advancedConfig?.quick_filters_2_2_6
    }?.split(",")?.map { it.trim() } ?: emptyList()
    val filterLabels = filters.map { filterProperty ->
        when (filterProperty) {
            TypeFilters.PROPERTY_TYPE.value -> currentPropertyType?.label_ru ?: "Тип недвижимости"
            else -> propLabels?.find { it.property == filterProperty }?.label_ru ?: filterProperty
        }
    }
    FilterButtons(
        configViewModel = configViewModel,
        filterEng = filters,
        searchViewModel = searchViewModel,
        filters = filterLabels,
        onFilterButtonClicked = onFilterButtonClicked,
        onFilterSelected = { selectedFilter ->
            println("Выбран фильтр: $selectedFilter")
        },
        currentFilters = currentFilters
    )
}
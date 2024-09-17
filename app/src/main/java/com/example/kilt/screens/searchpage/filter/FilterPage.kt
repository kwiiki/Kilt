package com.example.kilt.screens.searchpage.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.data.config.FilterItem
import com.example.kilt.screens.searchpage.homedetails.gradient
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterPage(
    configViewModel: ConfigViewModel,
    searchViewModel: SearchViewModel,
    onCloseFilterBottomSheet: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FilterContent(configViewModel = configViewModel, searchViewModel)
        ShowAnnouncementsButton(
            searchViewModel,
            Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .align(alignment = Alignment.BottomCenter),
            onCloseFilterBottomSheet
        )
    }

}

@Composable
fun ShowAnnouncementsButton(
    searchViewModel: SearchViewModel,
    modifier: Modifier,
    onButtonClick: () -> Unit
) {
    val searchCount by searchViewModel.searchResultCount.collectAsState()
    Row(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = {
                searchViewModel.performSearch()
               onButtonClick()
            },
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(gradient, RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Показать ${searchCount.toString()} вариантов",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun FilterContent(configViewModel: ConfigViewModel, searchViewModel: SearchViewModel) {
    val config by configViewModel.config.collectAsState()
    val listingProps by configViewModel.listingProps.collectAsState()


    LaunchedEffect(Unit) {
        configViewModel.loadHomeSale()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TypeOfHousing(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            configViewModel = configViewModel,
            searchViewModel = searchViewModel
        )
        Divider()
        LocationSection(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        Divider()
        OnlyOwnersSection()
        Divider()
        listingProps?.forEach { prop ->
            val matchingLabel = config?.propLabels?.find { it.property == prop }
            when (matchingLabel?.filter_type) {
                "list" -> ListFilter(configViewModel, prop, matchingLabel.label_ru, searchViewModel)
                "list-multiple" -> ListFilter(
                    configViewModel,
                    prop,
                    matchingLabel.label_ru,
                    searchViewModel
                )

                "range" -> RangeFilter(prop, matchingLabel.label_ru, searchViewModel)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun RangeFilter(prop: String, title: String, searchViewModel: SearchViewModel) {
    val (initialMin, initialMax) = searchViewModel.getRangeFilterValues(prop)
    var minValue by remember { mutableStateOf(if (initialMin > 0) initialMin.toString() else "") }
    var maxValue by remember { mutableStateOf(if (initialMax < Int.MAX_VALUE) initialMax.toString() else "") }


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
                onValueChange = { newValue ->
                    minValue = newValue.filter { it.isDigit() }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                onValueChange = { newValue ->
                    maxValue = newValue.filter { it.isDigit() }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
    Divider()

    LaunchedEffect(minValue, maxValue) {
        val min = minValue.toIntOrNull() ?: 0
        val max = maxValue.toIntOrNull() ?: 0
        searchViewModel.updateRangeFilter(prop, min, max)
    }
}

@Composable
fun ListFilter(
    viewModel: ConfigViewModel,
    prop: String,
    title: String,
    searchViewModel: SearchViewModel
) {
    val filters = viewModel.getFilterOptions(prop)
    val selectedFilters = searchViewModel.getSelectedFilters(prop)


    FilterButtons(
        filters = filters,
        title = title,
        selectedFilters = selectedFilters,
        onFilterSelected = { newSelectedFilters ->
            searchViewModel.updateListFilter(prop, newSelectedFilters)
        }
    )
}

@Composable
fun FilterButtons(
    filters: List<Any>?,
    title: String,
    selectedFilters: List<Int>,
    onFilterSelected: (List<Int>) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterButton(
                    text = "Все",
                    isSelected = selectedFilters.isEmpty(),
                    onClick = {
                        onFilterSelected(emptyList())
                    }
                )
            }
            items(filters ?: emptyList() ) { filter ->
                when (filter) {
                    is FilterItem -> {
                        FilterButton(
                            text = filter.name,
                            isSelected = selectedFilters.contains(filter.id),
                            onClick = {
                                val newSelectedFilters = if (filter.id in selectedFilters) {
                                    selectedFilters - filter.id
                                } else {
                                    selectedFilters + filter.id
                                }
                                onFilterSelected(newSelectedFilters)
                            }
                        )
                    }
                    else -> {
                    }
                }
            }
        }
    }
    Divider()
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(42.dp)
            .widthIn(min = 47.dp)
            .border(
                width = 1.5.dp,
                color = if (isSelected) Color.Blue else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Color.Blue.copy(alpha = 0.1f) else Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .widthIn(37.dp),
            color = if (isSelected) Color.Blue else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Divider() {
    Spacer(
        modifier = Modifier
            .height(1.5.dp)
            .fillMaxWidth()
            .background(Color(0xffe6e6e6))
    )
}
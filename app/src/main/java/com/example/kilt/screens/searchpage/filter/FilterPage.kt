package com.example.kilt.screens.searchpage.filter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.models.config.FilterItem
import com.example.kilt.presentation.search.FiltersViewModel
import com.example.kilt.presentation.search.SearchResultsViewModel
import com.example.kilt.presentation.listing.gradient
import com.example.kilt.presentation.choosecity.viewmodel.ChooseCityViewModel
import com.example.kilt.presentation.config.viewmodel.ConfigViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterPage(
    searchViewModel: SearchResultsViewModel,
    chooseCityViewModel: ChooseCityViewModel,
    navController: NavHostController,
    configViewModel: ConfigViewModel,
    filtersViewModel: FiltersViewModel,
    onCloseFilterBottomSheet: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FilterContent(
            chooseCityViewModel = chooseCityViewModel,
            navController = navController,
            configViewModel = configViewModel,
            filtersViewModel = filtersViewModel
        )
        ShowAnnouncementsButton(
            searchViewModel = searchViewModel,
            filtersViewModel = filtersViewModel,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .align(alignment = Alignment.BottomCenter),
            onButtonClick = onCloseFilterBottomSheet
        )
    }
}

@Composable
fun ShowAnnouncementsButton(
    searchViewModel: SearchResultsViewModel,
    filtersViewModel: FiltersViewModel,
    modifier: Modifier,
    onButtonClick: () -> Unit
) {
    val filtersState by filtersViewModel.filtersState.collectAsState()
    val sorting by filtersViewModel.sorting.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()

    OutlinedButton(
        onClick = {
            searchViewModel.updateFiltersAndPerformSearch(filtersState, sorting)
            onButtonClick()
        },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(gradient, RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
            } else {
                Text(
                    text = "Показать варианты",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun FilterContent(
    chooseCityViewModel: ChooseCityViewModel,
    navController: NavHostController,
    configViewModel: ConfigViewModel,
    filtersViewModel: FiltersViewModel
) {
    val config by configViewModel.config.collectAsState()
    val listingProps by configViewModel.listingProps.collectAsState()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        configViewModel.loadHomeSale()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TypeOfHousing(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                configViewModel = configViewModel,
                filtersViewModel = filtersViewModel
            )
            CustomDivider()
            LocationSection(
                chooseCityViewModel = chooseCityViewModel,
                navController,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            CustomDivider()
            OnlyOwnersSection()
            CustomDivider()

            Log.d("listingProps", "FilterContent: ${listingProps.toString()}")
            listingProps?.forEach { prop ->
                val matchingLabel = config?.propLabels?.find { it.property == prop }
                when (matchingLabel?.filter_type) {
                    "list", "list-multiple" -> ListFilter(
                        configViewModel,
                        prop,
                        matchingLabel.label_ru,
                        filtersViewModel
                    )
                    "range" -> RangeFilter(
                        prop = prop,
                        title = matchingLabel.label_ru,
                        filtersViewModel = filtersViewModel,
                        onFocusChanged = { isFocused -> /* handle focus */ },
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun ListFilter(
    configViewModel: ConfigViewModel,
    prop: String,
    title: String,
    filtersViewModel: FiltersViewModel
) {
    val filterOptions by configViewModel.getFilterOptions(prop).collectAsState(initial = emptyList())
    val selectedFilters by filtersViewModel.getSelectedFilters(prop).collectAsState(initial = emptyList())

    FilterButtons(
        filters = filterOptions,
        title = title,
        selectedFilters = selectedFilters,
        onFilterSelected = { newSelectedFilters ->
            filtersViewModel.updateListFilter(prop, newSelectedFilters)
        }
    )
}

@Composable
fun FilterButtons(
    filters: List<FilterItem>,
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
            items(filters) { filter ->
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
        }
    }
    CustomDivider()
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
            .widthIn(min = 42.dp)
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
fun CustomDivider() {
    Spacer(
        modifier = Modifier
            .height(1.5.dp)
            .fillMaxWidth()
            .background(Color(0xffe6e6e6))
    )
}
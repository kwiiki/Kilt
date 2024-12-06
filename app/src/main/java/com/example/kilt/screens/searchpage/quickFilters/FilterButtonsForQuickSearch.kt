package com.example.kilt.screens.searchpage.quickFilters

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kilt.models.config.FilterItem
import com.example.kilt.presentation.search.FiltersViewModel
import com.example.kilt.presentation.config.viewmodel.ConfigViewModel
import com.example.kilt.screens.searchpage.filter.FilterButton


@Composable
fun ListFilterForQuick(
    viewModel: ConfigViewModel,
    prop: String,
    title: String,
    filtersViewModel: FiltersViewModel
) {
    val filters by viewModel.getFilterOptions(prop).collectAsState(initial = emptyList())
    val selectedFilters by filtersViewModel.getSelectedFilters(prop).collectAsState(initial = emptyList())
    Log.d("selectedFilters", "ListFilter: $prop $selectedFilters")
    FilterButtonsForQuickSearch(
        filters = filters,
        title = title,
        selectedFilters = selectedFilters,
        onFilterSelected = { newSelectedFilters ->
            filtersViewModel.updateListFilter(prop, newSelectedFilters)
        }
    )
}

@Composable
fun FilterButtonsForQuickSearch(
    filters: List<Any>?,
    title: String,
    selectedFilters: List<Int>,
    onFilterSelected: (List<Int>) -> Unit
) {
    Column(modifier = Modifier.padding( vertical = 8.dp)) {
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
            items(filters ?: emptyList()) { filter ->
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
}
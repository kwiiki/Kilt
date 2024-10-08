package com.example.kilt.screens.searchpage.quickFilters

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.data.config.FilterItem
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel


@Composable
fun ListFilterForQuick(
    viewModel: ConfigViewModel,
    prop: String,
    title: String,
    searchViewModel: SearchViewModel
) {
    val filters = viewModel.getFilterOptions(prop)
    val selectedFilters = searchViewModel.getSelectedFilters(prop)
    Log.d("selectedFilters", "ListFilter: $prop $selectedFilters")
    FilterButtonsForQuickSearch(
        filters = filters,
        title = title,
        selectedFilters = selectedFilters,
        onFilterSelected = { newSelectedFilters ->
            searchViewModel.updateListFilter(prop, newSelectedFilters)
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
                com.example.kilt.screens.searchpage.filter.FilterButton(
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
                        com.example.kilt.screens.searchpage.filter.FilterButton(
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
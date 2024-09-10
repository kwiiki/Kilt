package com.example.kilt.screens.searchpage

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.SearchViewModel


@Composable
fun SearchPage(
    homeSaleViewModel: HomeSaleViewModel,
    configViewModel: ConfigViewModel,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val filters by remember { searchViewModel.filters }.collectAsState()
    val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()

    LaunchedEffect(filters) {
        Log.d("SearchPage", "Filters updated: $filters")
        // Не нужно вызывать performSearch() здесь, так как Pager автоматически обновится при изменении фильтров
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SearchAndFilterSection(configViewModel, searchViewModel)
        Spacer(modifier = Modifier.height(8.dp))
        when {
            searchResults.loadState.refresh is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            searchResults.loadState.refresh is LoadState.Error -> {
                val error = (searchResults.loadState.refresh as LoadState.Error).error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${error.message}", color = Color.Red)
                }
            }
            else -> {
                LazyColumn {
                    items(
                        count = searchResults.itemCount,
                        key = { index -> "${searchResults[index]?.id}_$index" }
                    ) { index ->
                        val property = searchResults[index]
                        property?.let {
                            HouseItem(homeSaleViewModel, it, navController, configViewModel)
                        }
                    }
                    when {
                        searchResults.loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }
                        searchResults.loadState.append is LoadState.Error -> {
                            item {
                                val error = (searchResults.loadState.append as LoadState.Error).error
                                Text(text = "Error: ${error.message}", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
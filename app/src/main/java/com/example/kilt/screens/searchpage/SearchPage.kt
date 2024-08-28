package com.example.kilt.screens.searchpage

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
    val searchResult by searchViewModel.searchResult.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val error by searchViewModel.error.collectAsState()
    val filters by searchViewModel.filters.collectAsState()

    // Обновление при изменении фильтров
    LaunchedEffect(filters) {
        searchViewModel.performSearch()
    }

    Log.d("SearchPage", "Search result: $searchResult")

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SearchAndFilterSection(configViewModel)
        Spacer(modifier = Modifier.height(8.dp))
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Нет подключение к интернету", color = Color.Gray, fontSize = 18.sp)
                }
            }
            searchResult != null -> {
                LazyColumn {
                    items(searchResult!!.list, key = { it.id }) { search ->
                        HouseItem(homeSaleViewModel, search, navController)
                        Log.d("search price", "SearchPage: ${searchResult!!.list[0]}")
                    }
                }
            }
        }
    }
}
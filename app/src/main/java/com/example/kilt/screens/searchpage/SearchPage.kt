package com.example.kilt.screens.searchpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kilt.R
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.SearchViewModel
import kotlinx.coroutines.launch


@Composable
fun SearchPage(
    homeSaleViewModel: HomeSaleViewModel,
    configViewModel: ConfigViewModel,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val filters by remember { searchViewModel.filters }.collectAsState()
    val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()
//    LaunchedEffect(filters) {
//        Log.d("SearchPage", "Filters updated: $filters")
//    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        onDispose {
            searchViewModel.saveScrollState(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset
            )
        }
    }
    // restore scroll position
    LaunchedEffect(searchResults.loadState.refresh) {
        if (searchResults.loadState.refresh is LoadState.NotLoading) {
            val (savedIndex, savedOffset) = searchViewModel.getSavedScrollState()
            if (savedIndex in 0 until searchResults.itemCount) {
                coroutineScope.launch {
                    listState.scrollToItem(savedIndex, savedOffset)
                }
            }
        }
    }
    Column(
        modifier = Modifier
    ) {
        SearchAndFilterSection(
            configViewModel,
            searchViewModel,
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        when {
            searchResults.loadState.refresh is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Gray)
                }
            }
            searchResults.loadState.refresh is LoadState.Error -> {
                val error = (searchResults.loadState.refresh as LoadState.Error).error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ERRRRROR", color = Color.Red)
                }
            }
            searchResults.itemCount == 0 && searchResults.loadState.refresh is LoadState.NotLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.image_empty),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(alignment = Alignment.Center)
                    ) {
                        Text(
                            text = "Нет объявлений",
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "По данным фильтрам ничего не найдено.",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            else -> {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    LazyColumn(state = listState) {
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
                                    val error =
                                        (searchResults.loadState.append as LoadState.Error).error
                                    Text(text = "Error: ${error.message}", color = Color.Red)
                                }
                            }
                            searchResults.loadState.append is LoadState.NotLoading &&
                                    searchResults.loadState.append.endOfPaginationReached -> {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Мы все показали!",
                                            textAlign = TextAlign.Center,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Попробуйте другие параметры поиска, чтобы увидеть больше вариантов",
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
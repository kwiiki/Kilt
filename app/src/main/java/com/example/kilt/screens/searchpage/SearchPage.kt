package com.example.kilt.screens.searchpage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
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
    val response by remember { searchViewModel.searchResult }.collectAsState()
    val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()

    LaunchedEffect(filters) {
        Log.d("SearchPage", "Filters updated: $filters")
        Log.d("SearchPage", "Filters updated: $response")
        Log.d("SearchPage", "Filters updated: $searchResults")
    }
    LaunchedEffect(searchResults.loadState) {
        Log.d("SearchPage", "LoadState: ${searchResults.loadState}")
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
                Log.d("SearchPage", "SearchPage: Loading")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Gray)
                }
            }
            searchResults.loadState.refresh is LoadState.Error -> {
                Log.d("SearchPage", "SearchPage: Error")
                val error = (searchResults.loadState.refresh as LoadState.Error).error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ERRRRROR", color = Color.Red)
                }
            }
            searchResults.itemCount == 0 && searchResults.loadState.refresh is LoadState.NotLoading -> {
                Log.d("SearchPage", "SearchPage: refresh and NotLoading")
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
                    Log.d("SearchPage", "Before LazyColumn, itemCount: ${searchResults.itemCount}")
//                    Text("Debug: itemCount=${searchResults.itemCount}, loadState=${searchResults.loadState}")
                    Log.d("Debug", "SearchPage: ${searchResults.loadState}")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(searchResults.itemCount) { index ->
                            val property = searchResults[index]
                            property?.let {
                                HouseItem(homeSaleViewModel, it, navController, configViewModel)
                            }
                        }
                        searchResults.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        Box(
                                            modifier = Modifier.fillParentMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                                loadState.refresh is LoadState.Error -> {
                                    val error = loadState.refresh as LoadState.Error
                                    item {
                                        ErrorMessage(
                                            modifier = Modifier.fillParentMaxSize(),
                                            message = error.error.localizedMessage ?: "Ошибка загрузки",
                                            onClickRetry = { retry() }
                                        )
                                    }
                                }
                                loadState.append is LoadState.Loading -> {
                                    item { LoadingNextPageItem(modifier = Modifier) }
                                }
                                loadState.append.endOfPaginationReached -> {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Мы показали все объявления!",
                                                textAlign = TextAlign.Center,
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Попробуйте изменить параметры поиска, чтобы увидеть больше вариантов.",
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
}
@Composable
fun LoadingNextPageItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onClickRetry) {
                Text(text = "Попробовать снова")
            }
        }
    }
}

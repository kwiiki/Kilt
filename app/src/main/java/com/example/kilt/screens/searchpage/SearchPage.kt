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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.SearchViewModel


@Composable
fun SearchPage(
    chooseCityViewModel: ChooseCityViewModel,
    homeSaleViewModel: HomeSaleViewModel,
    configViewModel: ConfigViewModel,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val response = searchViewModel.searchResult.collectAsState()
    val searchResults = searchViewModel.searchResultsFlow.collectAsLazyPagingItems()

    val listState by searchViewModel.listState.collectAsState()

    LaunchedEffect(searchResults.loadState) {
        Log.d("SearchPage", "LoadState: $response")
    }
    Log.d("SearchPage", "Refresh state: ${searchResults.loadState.refresh}")
    Log.d("SearchPage", "Append state: ${searchResults.loadState.append}")
    Column(
        modifier = Modifier
    ) {
        SearchAndQuickFilters(
            chooseCityViewModel,
            navController,
            configViewModel,
            searchViewModel,
            Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 8.dp)
        )
        when {
            searchResults.loadState.refresh is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Gray)
                }
            }
            searchResults.loadState.refresh is LoadState.Error -> {
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
                    LazyColumn(
                        state = listState, // Сохранение состояния скролла
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

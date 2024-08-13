package com.example.kilt.screens.searchpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.myapplication.data.HomeSale


val homeList = listOf<HomeSale>()

@Composable
fun SearchPage(navController: NavHostController) {
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    val homeSale by homeSaleViewModel.homeSale

    LaunchedEffect(Unit) {
        homeSaleViewModel.loadHomesale()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SearchAndFilterSection()
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(homeList) { home ->
                PropertyItem(home, navController)

            }
        }


    }


}

@Composable
@Preview(showBackground = true)
fun PreviewSaleAndRent() {
    val navController = rememberNavController()
    SearchPage(navController)
}
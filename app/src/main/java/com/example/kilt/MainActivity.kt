package com.example.kilt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.kilt.data.Listing
import com.example.kilt.network.RetrofitInstance
import com.example.kilt.network.SearchResponse
import com.example.kilt.screens.searchpage.SearchPage
import com.example.myapplication.data.HomeSale
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            KiltApp()
//            HomeDetailsScreen()
//            ProfileScreen()
//            val navController = rememberNavController()
//           SearchPage(navController = navController)
//            HomeDetailsScreen(navController)
//            val homeSaleViewModel: HomeSaleViewModel = viewModel()
//            val homeSale by homeSaleViewModel.homeSale
//            val navController = rememberNavController()
//
//            PropertyItem(homeSale = homeSale, navController = navController)
//
//            SearchPage(navController = navController)

        }
    }
}

@Composable
fun SearchScreen() {
    val coroutineScope = rememberCoroutineScope()
    var searchResult by remember { mutableStateOf<SearchResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        coroutineScope.launch {
            try {
                val request = HomeSale(
                    listing = Listing(

                        deal_type = 2,
                        listing_type = 1,
                        property_type = 1,
                        num_rooms = 3,
                        price = "1500000",
                        status = 1,
                        first_image = "",
                        num_floors = 1,
                        address_string = "",
                        floor = 32,
                        description = "",
                        built_year = 2010,
                        images = listOf(),
                        designation = "",
                        where_located = 2,
                        line_of_houses = 3,
                        ceiling_height = 2.5,
                        furniture = 3
                    ),

                    page = 0,
                    sorting = "new"
                )

                val response = RetrofitInstance.api.search(request)
                searchResult = response
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            searchResult != null -> {
                Log.d("results", "SearchScreen: ${searchResult.toString()}")
            }
        }
    }
}

















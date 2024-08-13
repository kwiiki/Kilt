package com.example.kilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.kilt.screens.searchpage.PropertyItem
import com.example.kilt.screens.searchpage.SearchPage
import com.example.kilt.screens.searchpage.filter.FilterPage
import com.example.kilt.screens.searchpage.homedetails.HomeDetailsScreen
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.myapplication.data.HomeSale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

//            KiltApp()
//            HomeDetailsScreen()
//            ProfileScreen()
//            val navController = rememberNavController()
//           SearchPage(navController = navController)
//            HomeDetailsScreen()Pr
            val homeSaleViewModel: HomeSaleViewModel = viewModel()
            val homeSale by homeSaleViewModel.homeSale // Получаем значение из MutableState
            val navController = rememberNavController()

            PropertyItem(homeSale = homeSale, navController = navController)
        }
    }
}
















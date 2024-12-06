package com.example.kilt.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.presentation.favorites.viewmodel.FavoritesViewModel
import com.example.kilt.screens.searchpage.HouseItem
import com.example.kilt.presentation.config.viewmodel.ConfigViewModel
import com.example.kilt.presentation.listing.viewmodel.ListingViewModel

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    favoritesViewModel: FavoritesViewModel,
    listingViewModel: ListingViewModel,
    configViewModel: ConfigViewModel
) {
    val favoritesListing by favoritesViewModel.favoritesListings.collectAsState()
    LaunchedEffect(Unit) {
        favoritesViewModel.getUserFavorites()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color(0xFF566982),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Избранное",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 24.sp,
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
        }
        if (favoritesListing.favorites.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 130.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favorite_star_img),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .padding(bottom = 32.dp)
                )
                Text(
                    text = "Добавляйте объявления в избранное",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Отмечайте интересные объявления и следите за изменением цены",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xff566982)),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SearchAnnouncementButton(navController)
            }
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(favoritesListing.favorites) { index ->
                    HouseItem(
                        listingViewModel = listingViewModel,
                        search = index,
                        navController = navController,
                        configViewModel = configViewModel,
                    )
                }
            }
        }
    }
}




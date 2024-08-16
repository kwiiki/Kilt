package com.example.kilt.screens.searchpage.homedetails

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.data.Home
import com.example.kilt.screens.searchpage.Chip
import com.example.kilt.screens.searchpage.GradientButton
import com.example.kilt.screens.searchpage.IconText
import com.example.kilt.screens.searchpage.WhatsAppGradientButton
import com.example.kilt.utills.imageCdnUrl
import com.example.kilt.viewmodels.HomeSaleViewModel


val gradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
)

@Composable
fun PropertyItemForSimilar(home: Home, navController: NavHostController) {
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    val topListings by homeSaleViewModel.topListings
    val homeSale by homeSaleViewModel.homeSale


    LaunchedEffect(Unit) {
        homeSaleViewModel.loadHomeSale()
    }
    Log.d("PropertyItem", "topListings: $topListings")


    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(250.dp)
            .background(Color(0xffFFFFFF))
            .clickable { navController.navigate("HomeDetails") },
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column(modifier = Modifier.background(Color(0xffFFFFFF))) {
            Box {
                AsyncImage(
                    model = "$imageCdnUrl${homeSale?.listing?.first_image}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(120.dp)
                )
                IconButton(
                    onClick = { /* TODO: Handle favorite click */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color(0xff6B6D79),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Chip(text = "Собственник")
            }

            Text(
                text = "${homeSale?.listing?.price} ₸",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(horizontal = 8.dp).padding(bottom = 3.dp)
            )

            Row(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                topListings.forEach { item ->
                    when (item.trim()) { // обьязательно нужен трим
                        "num_rooms" -> {
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.group_icon),
                                text = "${homeSale?.listing?.num_rooms} комн"
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        "area" -> {
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.room_icon),
                                text = "${homeSale?.listing?.area} м²"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        "floor" -> {
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.building_icon),
                                text = "${homeSale?.listing?.floor}/${homeSale?.listing?.num_floors}"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        else -> Log.d("PropertyItem", "Unexpected item in topListings: $item")
                    }
                }
            }

            Text(
                text = homeSale?.listing?.address_string.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xff6B6D79),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

        }
    }
}

package com.example.kilt.screens.searchpage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.data.PropertyItem
import com.example.kilt.navigation.NavPath
import com.example.kilt.screens.searchpage.homedetails.formatNumber
import com.example.kilt.screens.searchpage.homedetails.gradient
import com.example.kilt.utills.imageCdnUrl
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel


@Composable
fun HouseItem(
    homeSaleViewModel: HomeSaleViewModel,
    search: PropertyItem,
    navController: NavHostController,
    configViewModel: ConfigViewModel
) {
    val topListings by configViewModel.listingTop.collectAsState()

    LaunchedEffect(Unit) {
        homeSaleViewModel.loadHomeSale()
        homeSaleViewModel.loadConfig()
    }
    Log.d("HouseItem", "Recomposing item with id: ${search.id}")
    Log.d("PropertyItem", "topListings: $search")
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(Color(0xffFFFFFF))
            .clickable {
                navController.navigate("${NavPath.HOMEDETAILS.name}/${search.id}")
            },
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column(modifier = Modifier.background(Color(0xffFFFFFF))) {
            if (search.first_image == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_empty),
                        contentDescription = null,
                        modifier = Modifier.height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.camera_icon),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            } else {
                Box {
                    AsyncImage(
                        model = "${imageCdnUrl}${search.first_image}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(150.dp)
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
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Chip(text = "Собственник")
            }
            Text(
                text = "${formatNumber(search.price)} ₸",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
            )
            Log.d("houseItemPrice", "HouseItem: ${search.price}")
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Log.d("topListings", "HouseItem: ${topListings}")
                Log.d("topListings", "HouseItem: ${search.area} ${search.floor} ${search.num_rooms}"
                )

                topListings?.forEach { item ->
                    Log.d("", "Processing item: ${item.trim()}") // Added log
                    when (item.trim()) { // Ensure item is trimmed properly
                        "num_rooms" -> {
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.group_icon),
                                text = "${search.num_rooms} комн"
                            )
                            Log.d("topListings", "HouseItem num_rooms: ${search.num_rooms}")
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        "area" -> {
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.room_icon),
                                text = "${search.area} м²"
                            )
                            Log.d("topListings", "HouseItem area: ${search.area}")
                            Spacer(modifier = Modifier.width(10.dp))
                        }

                        "floor" -> {
                            if (search.floor != 0 && search.num_floors != null) {
                                IconText(
                                    icon = ImageVector.vectorResource(id = R.drawable.building_icon),
                                    text = "${search.floor}/${search.num_floors}"
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }

                        else -> Log.d("topListings", "Unexpected item in topListings: $item")
                    }
                }
            }
            Text(
                text = search.address_string,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 16.sp,
                color = Color(0xff6B6D79),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GradientButton(
                    text = "Позвонить",
                    textColor = Color.White,
                    gradient = gradient,
                    onClick = { /* TODO: Handle click */ },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                WhatsAppGradientButton(
                    text = "Написать",
                    onClick = { /* TODO: Handle click */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Composable
fun Chip(text: String) {
    Card(
        onClick = { /*TODO*/ },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),

        ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.chield_check_fill),
                contentDescription = null,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, color = Color(0xFF56B375))
        }
    }

}

@Composable
fun IconText(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xff6B6D79),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
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

    val imageList = search.images

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
                val pagerState = rememberPagerState(initialPage = 0)
                Box {
                    HorizontalPager(
                        count = if (imageList.size > 5) 5 else imageList.size,
                        state = pagerState,
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth()
                    ) { page ->
                        Log.d("ImageList", "HouseItem:${imageCdnUrl}${imageList[page].link}")
                        AsyncImage(
                            model = "${imageCdnUrl}${imageList[page].link}",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        pageCount = if (imageList.size > 5) 5 else imageList.size,
                        activeColor = Color(0xFFFFFFFF),
                        inactiveColor = Color(0xFFBBBBBB),
                    )
                    IconButton(
                        onClick = { /* TODO: Handle favorite click */ },
                        modifier = Modifier.align(Alignment.TopEnd)
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
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                topListings?.forEach { item ->
                    when (item.trim()) {
                        "num_rooms" -> {
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.group_icon),
                                text = "${search.num_rooms} комн"
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        "area" -> {
                            val area = search.area
                            val formattedArea = if (area.rem(1) == 0.0) {
                                area.toInt().toString()
                            } else {
                                area.toString()
                            }
                            IconText(
                                icon = ImageVector.vectorResource(id = R.drawable.room_icon),
                                text = "$formattedArea м²"
                            )
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
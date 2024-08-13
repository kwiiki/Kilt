package com.example.kilt.screens.searchpage.homedetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.data.Home
import com.example.kilt.screens.searchpage.IconText
import com.example.kilt.viewmodels.HomeSaleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDetailsScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val home: Home = Home(0, 555555, 3, 54, 3, 10, "Абая 33", homeImg = R.drawable.kv1)
    var isFullScreenPhotoVisible by remember { mutableStateOf(false) }
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    val homeSale by homeSaleViewModel.homeSale
    homeSaleViewModel.loadHomesale()
    val topListings by homeSaleViewModel.topListings
    LaunchedEffect(Unit) {
        homeSaleViewModel.loadHomesale()
    }

    Box(modifier = Modifier.fillMaxSize().padding(bottom = 0.dp)) {
        BottomSheetScaffold(
            sheetContainerColor = Color(0xffffffff),
            scaffoldState = scaffoldState,
            sheetPeekHeight = if (isFullScreenPhotoVisible) 0.dp else 215.dp,
            sheetContent = {
                if (!isFullScreenPhotoVisible) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(630.dp)
                            .padding(horizontal = 8.dp)
                            .background(Color(0xffffffff))
                            .verticalScroll(scrollState),
                    ) {
                        Text(
                            text = "${homeSale?.listing?.price} ₸",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W700,
                            modifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
                        )

                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            topListings.forEach { item ->
                                when (item.trim()) { // обьязательно нужен трим
                                    "num_rooms" -> {
                                        IconText(
                                            icon = ImageVector.vectorResource(id = R.drawable.group_icon),
                                            text = "${home.roomCount} комн"
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                    }
                                    "area" -> {
                                        IconText(
                                            icon = ImageVector.vectorResource(id = R.drawable.room_icon),
                                            text = "${home.homeArea} м²"
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                    "floor" -> {
                                        IconText(
                                            icon = ImageVector.vectorResource(id = R.drawable.building_icon),
                                            text = "${home.homeFloor}/${home.homeMaxFloor}"
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                    else -> Log.d("PropertyItem", "Unexpected item in topListings: $item")
                                }
                            }
                        }
                        Text(
                            text = homeSale?.listing?.address_string ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xff6B6D79),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xffDBDFE4))
                        )
                        Calculator()

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xffDBDFE4))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val mapImage = painterResource(id = R.drawable.map_image)
                        Image(
                            painter = mapImage,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp), contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Описание",
                            color = Color.Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        ApartmentDescription()
                        Text(
                            text = "Информация",
                            color = Color.Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                        InfoSection()

                        Text(
                            text = "О Доме",
                            color = Color.Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )

                        InfoHomeSection()

                        Spacer(modifier = Modifier.height(110.dp))
                    }
                }
            }
        ) { innerPadding ->
            // ImageSlider
            ImageSlider(
                Modifier.padding(innerPadding),
                onFullScreenToggle = { isFullScreen ->
                    isFullScreenPhotoVisible = isFullScreen
                },
                navController
            )
        }
        if (!isFullScreenPhotoVisible) {
            BottomDetails(modifier = Modifier.zIndex(1f))
        }
    }
}

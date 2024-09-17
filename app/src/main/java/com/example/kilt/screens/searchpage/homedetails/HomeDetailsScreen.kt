package com.example.kilt.screens.searchpage.homedetails

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.screens.searchpage.IconText
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDetailsScreen(
    configViewModel: ConfigViewModel,
    navController: NavHostController,
    id: String?,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isFullScreenPhotoVisible by remember { mutableStateOf(false) }
    val homeSaleViewModel: HomeSaleViewModel = hiltViewModel()
    val homeSale by homeSaleViewModel.home.collectAsState()
    val listingsTop by configViewModel.listingTop.collectAsState()
    val isLoading by homeSaleViewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        id?.let {
            homeSaleViewModel.loadConfig()
            homeSaleViewModel.loadById(it)

        }
    }

    var openBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val searchResult by searchViewModel.searchResult.collectAsState()

    val propertyItem = remember(searchResult, id) {
        id?.let { searchViewModel.getPropertyById(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp)
    ) {
        if (openBottomSheet) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { openBottomSheet = false },
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomSheetDefaults.DragHandle(color = Color(0xff010101))
                    }
                }
            ) {
                BottomSheetContent(propertyItem?.price.toString(),
                    onHideButtonClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) openBottomSheet = false
                        }
                    }
                )
            }
        }
        BottomSheetScaffold(
            sheetContainerColor = Color(0xffffffff),
            scaffoldState = scaffoldState,
            sheetPeekHeight = if (isFullScreenPhotoVisible) 0.dp else 180.dp,
            sheetContent = {
                if (!isFullScreenPhotoVisible) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp)
                            .padding(horizontal = 8.dp)
                            .background(Color(0xffffffff))
                            .verticalScroll(scrollState),
                    ) {
                        Log.d("testPrice", "HomeDetailsScreen: ${homeSale?.listing?.price}")
                        AnimatedVisibility(
                            visible = !isLoading,
                            enter = fadeIn() + expandVertically()
                        ) {
                            Text(
                                text = "${formatNumber(homeSale?.listing?.price.toString())} ₸",
                                style = MaterialTheme.typography.labelMedium,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            listingsTop?.forEach { item ->
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

                                    else -> Log.d(
                                        "PropertyItem",
                                        "Unexpected item in topListings: $item"
                                    )
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = !isLoading,
                            enter = fadeIn() + expandVertically()
                        ) {
                            Text(
                                text = homeSale?.listing?.address_string.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xff6B6D79),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xffDBDFE4))
                        )
                        Calculator(onClick = { openBottomSheet = true })
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = homeSale?.listing?.description ?: "",
                            fontSize = 16.sp,
                            color = Color(0xff566982),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = "Информация",
                            color = Color.Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                        InfoSection(homeSale,homeSaleViewModel)
                        Spacer(modifier = Modifier.height(8.dp))
                        val builtYear = homeSaleViewModel.homeSale.value?.listing?.built_year
                        if (builtYear != null && builtYear != 0) {
                            Text(
                                text = "О Доме",
                                color = Color.Black,
                                fontWeight = FontWeight.W700,
                                fontSize = 22.sp,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                            )
                            InfoHomeSection(homeSaleViewModel)
                        }
//                        Text(
//                            text = "Похожие",
//                            color = Color.Black,
//                            fontWeight = FontWeight.W700,
//                            fontSize = 22.sp,
//                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
//                        )
//                        Row {
//
//                            LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
//                                items(homeList) { home ->
//                                    PropertyItemForSimilar(home, navController)
//                                }
//                            }
//                        }
                        Spacer(modifier = Modifier.height(110.dp))
                    }
                }
            }
        ) { innerPadding ->
            Log.d("images", "HomeDetailsScreen: ${propertyItem?.images}")
            ImageSlider(
                homeSale?.listing?.images,
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
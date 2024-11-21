@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.presentation.search

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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kilt.KiltApplication
import com.example.kilt.R
import com.example.kilt.presentation.search.viewmodel.MapViewModel
import com.example.kilt.screens.searchpage.HouseItem
import com.example.kilt.screens.searchpage.SearchAndQuickFilters
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.kilt.viewmodels.SearchViewModel
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.geometry.Elevation
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.ClusterObject
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.MapTheme
import ru.dgis.sdk.map.MapView
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.Zoom
import ru.dgis.sdk.map.imageFromResource


@Composable
fun SearchPage(
    chooseCityViewModel: ChooseCityViewModel,
    homeSaleViewModel: HomeSaleViewModel,
    configViewModel: ConfigViewModel,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    val sdkContext = (context.applicationContext as KiltApplication).sdkContext
    val response = searchViewModel.searchResult
    val searchResults = searchViewModel.searchResultsFlow.collectAsLazyPagingItems()
    val listState by searchViewModel.listState.collectAsState()
    val points = response.value?.map
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coordinates by searchViewModel.propertyCoordinates.collectAsState(initial = emptyList()) // Observing property coordinates

    LaunchedEffect(searchResults.loadState) {
        Log.d("SearchPage", "LoadState: ${coordinates.size}")
        Log.d("SearchPage", "LoadState: ${response.value?.list?.get(0).toString()}")
    }
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
//        BottomSheetScaffold(
//            sheetContainerColor = Color(0xffffffff),
//            scaffoldState = scaffoldState,
//            sheetDragHandle = {},
//            sheetPeekHeight = 130.dp,
//            sheetContent = {
                when {
                    searchResults.loadState.refresh is LoadState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.Gray)
                        }
                    }

                    searchResults.loadState.refresh is LoadState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error: ERRRRROR", color = Color.Red)
                        }
                    }

                    searchResults.itemCount == 0 && searchResults.loadState.refresh is LoadState.NotLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
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
                                        HouseItem(
                                            homeSaleViewModel,
                                            it,
                                            navController,
                                            configViewModel
                                        )
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
                                                    message = error.error.localizedMessage
                                                        ?: "Ошибка загрузки",
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
//        ) {
//            val mapViewModel: MapViewModel = viewModel()
//
//            AndroidView(
//                factory = { context ->
//                    val mapView = MapView(context).apply {
//                        getMapAsync { map ->
//                            mapViewModel.mapObjectManager ?: run {
//                                mapViewModel.mapObjectManager = MapObjectManager(map)
//                            }
//
//                            // Установите позицию камеры
//                            map.camera.move(
//                                CameraPosition(
//                                    point = GeoPoint(43.238949, 76.889709),
//                                    zoom = Zoom(value = 12.0f)
//                                )
//                            )
//
//                            val mapObjectManager = mapViewModel.mapObjectManager ?: return@getMapAsync
//
//                            mapObjectManager.removeAll()
//
//                            properties.forEach { property ->
//                                val marker = Marker(
//                                    MarkerOptions(
//                                        position = GeoPointWithElevation(
//                                            latitude = property.lat,
//                                            longitude = property.lng,
//                                            elevation = Elevation(0f)
//                                        ),
//                                        text = "ID: ${property.id}",
//                                        icon = imageFromResource(sdkContext, R.drawable.button_image_for_tarif),
//                                        draggable = false
//                                    )
//                                )
//                                mapObjectManager.addObject(marker)
//                            }
//                        }
//                    }
//                    mapView.setTheme(MapTheme.defaultTheme)
//                    mapView
//                },
//                modifier = Modifier.fillMaxSize()
//            )
        }
    }
//}
//data class Property(
//    val id: Int,
//    val lat: Double,
//    val lng: Double
//)
//val properties = listOf(
//    Property(776926, 43.242108, 76.936383),
//    Property(776903, 43.294637, 76.91335),
//    Property(776864, 43.206511012143, 76.891760364306),
//    Property(776755, 43.289191, 77.015049),
//    Property(776712, 43.347874, 76.9653),
//    Property(776528, 43.264847, 76.932502),
//    Property(776488, 43.249483, 76.955571),
//    Property(776458, 43.206879538099, 76.784853367198),
//    Property(776421, 43.239173, 76.890327),
//    Property(753644, 43.680368, 77.117097),
//    Property(753501, 43.252576, 76.895941),
//    Property(753489, 43.236699199393, 76.828285153665),
//    Property(753418, 43.288447548674, 76.949766920982),
//    Property(753414, 43.267024653886, 76.929628973333),
//    Property(753309, 43.227095877451, 76.779794168008),
//    Property(753052, 43.311623, 76.940138),
//    Property(752740, 43.2466, 76.854322),
//    Property(752720, 43.254736, 76.85628),
//    Property(752683, 43.269002, 76.93827),
//    Property(752659, 43.248104, 76.943624),
//    Property(752637, 43.267282487614, 76.854794022802),
//    Property(752500, 43.323434556235, 77.03106880188),
//    Property(752379, 43.259974462876, 76.900437817005),
//    Property(752360, 43.244886, 76.813709),
//    Property(752188, 43.241040930075, 76.877635825763),
//    Property(750253, 43.267102151835, 76.929931640407),
//    Property(673747, 43.21241, 76.778576),
//    Property(673654, 43.223867181315, 76.951704455102),
//    Property(671001, 43.237163, 76.945654),
//    Property(670985, 43.233955872166, 76.751261870352),
//    Property(670871, 43.212454965445, 76.98091768343),
//    Property(669829, 43.392718054547, 76.968478160616),
//    Property(669806, 43.265765990798, 76.899751875098),
//    Property(669353, 43.231061, 76.947055),
//    Property(668310, 43.266744, 76.901223),
//    Property(668284, 43.248856911735, 76.866705268403),
//    Property(666922, 43.238142, 76.873896),
//    Property(665833, 43.316451, 77.029754),
//    Property(665750, 43.222298, 76.845851),
//    Property(662925, 43.243484398146, 76.830862577664),
//    Property(662912, 43.254999188579, 76.919920452804),
//    Property(662888, 43.243455, 76.8304),
//    Property(662498, 43.255227032263, 76.923174881665),
//    Property(661950, 43.261014, 76.935786),
//    Property(661871, 43.207909039956, 76.895041477282),
//    Property(661870, 43.207909039956, 76.895041477282),
//    Property(661869, 43.208523854873, 76.892832802624),
//    Property(661868, 43.204436181418, 76.897387504578),
//    Property(658641, 43.360264, 77.014321),
//    Property(658637, 43.206134340331, 76.89607005303),
//    Property(658632, 43.260494, 76.944612),
//    Property(658630, 43.232125, 76.885754)
//)

//class MyMapObjectSource(sdkContext: Context) : Source(sdkContext.nativePtr) {
//    private val objects = mutableListOf<Marker>()
//
//    fun addObject(marker: Marker) {
//        objects.add(marker)
//    }
//
//    fun removeObject(marker: Marker) {
//        objects.remove(marker)
//    }
//
//    fun getObjects(): List<Marker> = objects
//}

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

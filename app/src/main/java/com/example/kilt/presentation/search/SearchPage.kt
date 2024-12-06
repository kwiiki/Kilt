@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.presentation.search

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kilt.KiltApplication
import com.example.kilt.models.PropertyCoordinate
import com.example.kilt.presentation.search.map.createBitmapFromXml
import com.example.kilt.presentation.search.map.createClusterBitmap
import com.example.kilt.presentation.search.viewmodel.MapViewModel
import com.example.kilt.screens.searchpage.HouseItem
import com.example.kilt.screens.searchpage.SearchAndQuickFilters
import com.example.kilt.presentation.choosecity.viewmodel.ChooseCityViewModel
import com.example.kilt.presentation.config.viewmodel.ConfigViewModel
import com.example.kilt.presentation.listing.viewmodel.ListingViewModel
import com.example.kilt.presentation.search.map.MapViewWithMarkers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.geometry.Elevation
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.CameraState
import ru.dgis.sdk.map.LogicalPixel
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.MapTheme
import ru.dgis.sdk.map.Map
import ru.dgis.sdk.map.MapView
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.ScreenDistance
import ru.dgis.sdk.map.ScreenPoint
import ru.dgis.sdk.map.SimpleClusterObject
import ru.dgis.sdk.map.SimpleClusterOptions
import ru.dgis.sdk.map.SimpleClusterRenderer
import ru.dgis.sdk.map.TouchEventsObserver
import ru.dgis.sdk.map.Zoom
import ru.dgis.sdk.map.imageFromBitmap

import androidx.compose.material3.MaterialTheme as MaterialTheme1


@Composable
fun SearchPage(
    chooseCityViewModel: ChooseCityViewModel,
    configViewModel: ConfigViewModel,
    filtersViewModel: FiltersViewModel,
    searchResultsViewModel: SearchResultsViewModel,
    listingViewModel: ListingViewModel,
    mapViewModel: MapViewModel,
    navController: NavHostController
) {
    val searchResults = searchResultsViewModel.searchResultsFlow.collectAsLazyPagingItems()
    val mapPoints by searchResultsViewModel.pointsFlow.collectAsState()
    val listingsByIds = searchResultsViewModel.listingsByIdsFlow.collectAsLazyPagingItems()
    val listing by searchResultsViewModel.listingFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchAndQuickFilters(
            chooseCityViewModel = chooseCityViewModel,
            navController = navController,
            configViewModel = configViewModel,
            filtersViewModel = filtersViewModel,
            searchResultViewModel = searchResultsViewModel,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp)
        )
        Log.d("listings size", "SearchPage: ${listingsByIds.itemCount}")
        BottomSheetScaffold(
            containerColor = Color.White,
            contentColor = Color.White,
            sheetContainerColor = Color.White,
            sheetContentColor = Color.White,
            scaffoldState = rememberBottomSheetScaffoldState(),
            sheetPeekHeight = 64.dp,
            sheetDragHandle = { },
            sheetContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier
                        .height(4.dp)
                        .width(35.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color.Gray))
                }
                when {
                    listing != null -> {
                        SingleListingContent(
                            listing = listing!!,
                            listingViewModel = listingViewModel,
                            configViewModel = configViewModel,
                            navController = navController,
                        )
                    }
                    listingsByIds.itemCount > 0 -> {
                        ListingsByIdsContent(
                            listingsByIds = listingsByIds,
                            listingViewModel = listingViewModel,
                            navController = navController,
                            configViewModel = configViewModel
                        )
                    }
                    else -> {
                        SearchResultsContent(
                            searchResults = searchResults,
                            listingViewModel = listingViewModel,
                            navController = navController,
                            configViewModel = configViewModel
                        )
                    }
                }
            },
        ) {
            if (mapPoints.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.background(color = Color.Gray))
                }
            } else {
                MapViewWithMarkers(searchResultsViewModel, filtersViewModel, mapPoints = mapPoints)
            }
        }
    }
}
@Composable
fun ErrorMessage(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message, color = Color.Red, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Повторить")
            }
        }
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Нет доступных объявлений",
            style = MaterialTheme1.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingNextPageItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

data class Property(
    val id: Int,
    val lat: Double,
    val lng: Double
)

val properties = listOf(
    Property(909249, 43.252097, 76.947675),
    Property(909240, 43.145346917701, 76.899780151069),
    Property(909239, 43.259098618687, 76.885704883125),
    Property(909229, 43.23068, 76.885943),
    Property(909218, 43.301566, 76.872612),
    Property(909207, 43.209283, 76.896381),
    Property(909206, 43.280081585708, 76.853098033145),
    Property(909205, 43.244808550999, 76.882296964979),
    Property(909201, 43.213981, 76.940336),
    Property(909194, 43.250167829741, 76.925161716328),
    Property(909190, 43.222811, 76.882332),
    Property(909182, 43.251466, 76.911347),
    Property(909166, 43.28309061969, 76.879493138562),
    Property(909160, 43.225747, 76.90037),
    Property(909159, 43.264972, 76.915174),
    Property(909158, 43.230693, 76.962964),
    Property(909152, 43.251473, 76.911356),
    Property(909151, 43.241616, 76.884164),
    Property(909131, 43.199596, 76.880777),
    Property(909112, 43.230332, 76.947163),
    Property(909099, 43.200989316482, 76.914425476046),
    Property(909093, 43.281839, 76.949639),
    Property(909088, 43.224446, 76.861491),
    Property(909083, 43.304077027599, 76.880660452032),
    Property(909081, 43.23427341137, 76.881579112371),
    Property(909074, 43.242135, 76.872818),
    Property(909072, 43.237235, 76.898169),
    Property(909063, 43.220327, 76.845474),
    Property(909058, 43.253692945989, 76.862749991389),
    Property(909057, 43.263921, 76.932206),
    Property(909056, 43.198052, 76.907233),
    Property(909054, 43.276072, 76.894073),
    Property(909051, 43.219342, 76.842725),
    Property(909050, 43.199925, 76.90125),
    Property(909048, 43.273328, 76.882547),
    Property(909047, 43.237124, 76.877005),
    Property(909046, 43.240066, 76.957691),
    Property(909045, 43.259593022654, 76.865124912119),
    Property(909044, 43.252298349321, 76.896736110508),
    Property(909043, 43.307818, 76.839554),
    Property(909038, 43.217535, 76.901798),
    Property(909030, 43.253634511489, 76.862957090234),
    Property(909027, 43.256860612814, 76.936333015203),
    Property(909024, 43.235193, 76.955616),
    Property(909022, 43.322124, 76.902499),
    Property(909020, 43.235971593367, 76.922157104076),
    Property(909019, 43.27296, 76.934416),
    Property(909015, 43.244945, 76.922468),
    Property(909009, 43.237754, 76.897477),
    Property(909001, 43.266130888735, 76.929350155078),
    Property(908989, 43.271175, 76.949049),
    Property(908987, 43.234593247147, 76.893238613311),
    Property(908983, 43.19957, 76.900792),
    Property(908981, 43.201844, 76.849094),
    Property(908980, 43.273152746575, 76.950253525042),
    Property(908976, 43.218724, 76.939366),
    Property(908975, 43.247881271427, 76.868811406082),
    Property(908974, 43.246981, 76.902589),
    Property(908973, 43.233609649091, 76.882290818256),
    Property(908968, 43.238089, 76.903729),
    Property(908966, 43.244768, 76.895815),
    Property(908965, 43.14535379194, 76.899784035421),
    Property(908963, 43.227458489673, 76.834178483013),
    Property(908962, 43.294906180115, 76.831057976591),
    Property(908961, 43.195009, 76.850684),
    Property(908960, 43.251473, 76.911356),
    Property(908958, 43.293935103445, 76.896205453679),
    Property(908955, 43.169033344865, 76.873052802102),
    Property(908954, 43.31285, 76.911769),
    Property(908949, 43.240243, 76.832457),
    Property(908944, 43.235666, 76.849669),
    Property(908940, 43.224492, 76.87219),
    Property(908934, 43.224834, 76.842033),
    Property(908915, 43.235048, 76.922468),
    Property(908890, 43.282566371221, 76.840127578799),
    Property(908884, 43.24924, 76.924912),
    Property(908867, 43.256588, 76.858014),
    Property(908858, 43.260258, 76.916459),
    Property(908836, 43.293407137637, 76.893225190072),
    Property(908825, 43.268024, 76.951744),
    Property(908820, 43.308147688846, 76.877957660854),
    Property(908818, 43.229071, 76.934533),
    Property(908799, 43.215440372548, 76.85440422222),
    Property(908769, 43.191171, 76.896444),
    Property(908768, 43.205682354427, 76.900377235364),
    Property(908766, 43.195193, 76.899193),
    Property(908759, 43.276518, 76.932619),
    Property(908755, 43.203158, 76.871785),
    Property(908754, 43.214552, 76.854798),
    Property(908753, 43.232414, 76.885718),
    Property(908742, 43.265556, 76.853208),
    Property(908740, 43.260973, 76.947298),
    Property(908739, 43.224873, 76.936841),
    Property(908738, 43.238095717421, 76.93657821726),
    Property(908737, 43.167867197258, 76.916281844148),
    Property(908736, 43.226673, 76.845222),
    Property(908730, 43.200711, 76.895782),
    Property(908728, 43.221076, 76.842617),
    Property(908726, 43.263343, 76.868929),
    Property(908725, 43.248452, 76.877184),
    Property(908724, 43.216149596656, 76.8755718725),
    Property(908663, 43.210461194452, 76.84544146786),
    Property(908629, 43.195739, 76.88641),
    Property(908626, 43.22507, 76.899426),
    Property(908624, 43.278657101984, 76.888983688342),
    Property(908619, 43.186819, 76.866099),
    Property(908616, 43.190211, 76.856155),
    Property(908615, 43.2316483656, 76.831370737865),
    Property(908602, 43.291717, 76.959632),
    Property(908601, 43.313925, 76.962183),
    Property(908599, 43.278039202595, 76.953108294546),
    Property(908598, 43.218431692805, 76.886331613581),
    Property(908583, 43.266524, 76.922785),
    Property(908579, 43.221569, 76.829888),
    Property(908577, 43.296461, 76.955185),
    Property(908575, 43.266829, 76.858329),
    Property(908574, 43.300431, 76.835395),
    Property(908573, 43.156675156095, 76.904156581595),
    Property(908572, 43.207732, 76.889231),
    Property(908569, 43.314051, 76.957662),
    Property(908568, 43.241987831969, 76.872232326543),
    Property(908566, 43.301216608084, 76.867377187042),
    Property(908565, 43.184979897364, 76.880004210538),
    Property(908555, 43.226438521119, 76.959328517283),
    Property(908554, 43.202093844471, 76.9469585678),
    Property(908542, 43.25397781409, 76.829919892504),
    Property(908538, 43.260929906792, 76.843794252377),
    Property(908527, 43.324262, 76.930364),
    Property(908520, 43.186846, 76.831819),
    Property(908508, 43.242493, 76.958898),
    Property(908490, 43.266403, 76.886931),
    Property(908488, 43.324452, 76.893183),
    Property(908485, 43.286481220636, 76.956698665717),
    Property(908484, 43.197677205217, 76.848945933832),
    Property(908482, 43.260041, 76.870357),
    Property(908480, 43.185853, 76.952203),
    Property(908479, 43.17373, 76.896291),
    Property(908478, 43.220779100142, 76.941615115046),
    Property(908476, 43.160744, 76.853621),
    Property(909046, 43.240066, 76.957691),
    Property(909045, 43.259593022654, 76.865124912119),
    Property(909044, 43.252298349321, 76.896736110508),
    Property(909043, 43.307818, 76.839554),
    Property(909038, 43.217535, 76.901798),
    Property(909030, 43.253634511489, 76.862957090234),
    Property(909027, 43.256860612814, 76.936333015203),
    Property(909024, 43.235193, 76.955616),
    Property(909022, 43.322124, 76.902499),
    Property(909020, 43.235971593367, 76.922157104076),
    Property(909019, 43.27296, 76.934416),
    Property(909015, 43.244945, 76.922468),
    Property(909009, 43.237754, 76.897477),
    Property(909001, 43.266130888735, 76.929350155078),
    Property(908989, 43.271175, 76.949049),
    Property(908987, 43.234593247147, 76.893238613311),
    Property(908983, 43.19957, 76.900792),
    Property(908981, 43.201844, 76.849094),
    Property(908980, 43.273152746575, 76.950253525042),
    Property(908976, 43.218724, 76.939366),
    Property(908975, 43.247881271427, 76.868811406082),
    Property(908974, 43.246981, 76.902589),
    Property(908973, 43.233609649091, 76.882290818256),
    Property(908968, 43.238089, 76.903729),
    Property(908966, 43.244768, 76.895815),
    Property(908965, 43.14535379194, 76.899784035421),
    Property(908963, 43.227458489673, 76.834178483013),
    Property(908962, 43.294906180115, 76.831057976591),
    Property(908961, 43.195009, 76.850684),
    Property(908960, 43.251473, 76.911356),
    Property(908958, 43.293935103445, 76.896205453679),
    Property(908955, 43.169033344865, 76.873052802102),
    Property(908954, 43.31285, 76.911769),
    Property(908949, 43.240243, 76.832457),
    Property(908944, 43.235666, 76.849669),
    Property(908940, 43.224492, 76.87219),
    Property(908934, 43.224834, 76.842033),
    Property(908915, 43.235048, 76.922468),
    Property(908890, 43.282566371221, 76.840127578799),
    Property(908884, 43.24924, 76.924912),
    Property(908867, 43.256588, 76.858014),
    Property(908858, 43.260258, 76.916459),
    Property(908836, 43.293407137637, 76.893225190072),
    Property(908825, 43.268024, 76.951744),
    Property(908820, 43.308147688846, 76.877957660854),
    Property(908818, 43.229071, 76.934533),
    Property(908799, 43.215440372548, 76.85440422222),
    Property(908769, 43.191171, 76.896444),
    Property(908768, 43.205682354427, 76.900377235364),
    Property(908766, 43.195193, 76.899193),
    Property(908759, 43.276518, 76.932619),
    Property(908755, 43.203158, 76.871785),
    Property(908754, 43.214552, 76.854798),
    Property(908753, 43.232414, 76.885718),
    Property(908742, 43.265556, 76.853208),
    Property(908740, 43.260973, 76.947298),
    Property(908739, 43.224873, 76.936841),
    Property(908738, 43.238095717421, 76.93657821726),
    Property(908737, 43.167867197258, 76.916281844148),
    Property(908736, 43.226673, 76.845222),
    Property(908730, 43.200711, 76.895782),
    Property(908728, 43.221076, 76.842617),
    Property(908726, 43.263343, 76.868929),
    Property(908725, 43.248452, 76.877184),
    Property(908724, 43.216149596656, 76.8755718725),
    Property(908663, 43.210461194452, 76.84544146786),
    Property(908629, 43.195739, 76.88641),
    Property(908626, 43.22507, 76.899426),
    Property(908624, 43.278657101984, 76.888983688342),
    Property(908619, 43.186819, 76.866099),
    Property(908616, 43.190211, 76.856155),
    Property(908615, 43.2316483656, 76.831370737865),
    Property(908602, 43.291717, 76.959632),
    Property(908601, 43.313925, 76.962183),
    Property(908599, 43.278039202595, 76.953108294546),
    Property(908598, 43.218431692805, 76.886331613581),
    Property(908583, 43.266524, 76.922785),
    Property(908579, 43.221569, 76.829888),
    Property(908577, 43.296461, 76.955185),
    Property(908575, 43.266829, 76.858329),
    Property(908574, 43.300431, 76.835395),
    Property(908573, 43.156675156095, 76.904156581595),
    Property(908572, 43.207732, 76.889231),
    Property(908569, 43.314051, 76.957662),
    Property(908568, 43.241987831969, 76.872232326543),
    Property(908566, 43.301216608084, 76.867377187042),
    Property(908565, 43.184979897364, 76.880004210538),
    Property(908555, 43.226438521119, 76.959328517283),
    Property(908554, 43.202093844471, 76.9469585678),
    Property(908542, 43.25397781409, 76.829919892504),
    Property(908538, 43.260929906792, 76.843794252377),
    Property(908527, 43.324262, 76.930364),
    Property(908520, 43.186846, 76.831819),
    Property(908508, 43.242493, 76.958898),
    Property(908490, 43.266403, 76.886931),
    Property(908488, 43.324452, 76.893183),
    Property(908485, 43.286481220636, 76.956698665717),
    Property(908484, 43.197677205217, 76.848945933832),
    Property(908482, 43.260041, 76.870357),
    Property(908480, 43.185853, 76.952203),
    Property(908479, 43.17373, 76.896291),
    Property(908478, 43.220779100142, 76.941615115046),
    Property(908476, 43.160744, 76.853621),
    Property(909046, 43.240066, 76.957691),
    Property(909045, 43.259593022654, 76.865124912119),
    Property(909044, 43.252298349321, 76.896736110508),
    Property(909043, 43.307818, 76.839554),
    Property(909038, 43.217535, 76.901798),
    Property(909030, 43.253634511489, 76.862957090234),
    Property(909027, 43.256860612814, 76.936333015203),
    Property(909024, 43.235193, 76.955616),
    Property(909022, 43.322124, 76.902499),
    Property(909020, 43.235971593367, 76.922157104076),
    Property(909019, 43.27296, 76.934416),
    Property(909015, 43.244945, 76.922468),
    Property(909009, 43.237754, 76.897477),
    Property(909001, 43.266130888735, 76.929350155078),
    Property(908989, 43.271175, 76.949049),
    Property(908987, 43.234593247147, 76.893238613311),
    Property(908983, 43.19957, 76.900792),
    Property(908981, 43.201844, 76.849094),
    Property(908980, 43.273152746575, 76.950253525042),
    Property(908976, 43.218724, 76.939366),
    Property(908975, 43.247881271427, 76.868811406082),
    Property(908974, 43.246981, 76.902589),
    Property(908973, 43.233609649091, 76.882290818256),
    Property(908968, 43.238089, 76.903729),
    Property(908966, 43.244768, 76.895815),
    Property(908965, 43.14535379194, 76.899784035421),
    Property(908963, 43.227458489673, 76.834178483013),
    Property(908962, 43.294906180115, 76.831057976591),
    Property(908961, 43.195009, 76.850684),
    Property(908960, 43.251473, 76.911356),
    Property(908958, 43.293935103445, 76.896205453679),
    Property(908955, 43.169033344865, 76.873052802102),
    Property(908954, 43.31285, 76.911769),
    Property(908949, 43.240243, 76.832457),
    Property(908944, 43.235666, 76.849669),
    Property(908940, 43.224492, 76.87219),
    Property(908934, 43.224834, 76.842033),
    Property(908915, 43.235048, 76.922468),
    Property(908890, 43.282566371221, 76.840127578799),
    Property(908884, 43.24924, 76.924912),
    Property(908867, 43.256588, 76.858014),
    Property(908858, 43.260258, 76.916459),
    Property(908836, 43.293407137637, 76.893225190072),
    Property(908825, 43.268024, 76.951744),
    Property(908820, 43.308147688846, 76.877957660854),
    Property(908818, 43.229071, 76.934533),
    Property(908799, 43.215440372548, 76.85440422222),
    Property(908769, 43.191171, 76.896444),
    Property(908768, 43.205682354427, 76.900377235364),
    Property(908766, 43.195193, 76.899193),
    Property(908759, 43.276518, 76.932619),
    Property(908755, 43.203158, 76.871785),
    Property(908754, 43.214552, 76.854798),
    Property(908753, 43.232414, 76.885718),
    Property(908742, 43.265556, 76.853208),
    Property(908740, 43.260973, 76.947298),
    Property(908739, 43.224873, 76.936841),
    Property(908738, 43.238095717421, 76.93657821726),
    Property(908737, 43.167867197258, 76.916281844148),
    Property(908736, 43.226673, 76.845222),
    Property(908730, 43.200711, 76.895782),
    Property(908728, 43.221076, 76.842617),
    Property(908726, 43.263343, 76.868929),
    Property(908725, 43.248452, 76.877184),
    Property(908724, 43.216149596656, 76.8755718725),
    Property(908663, 43.210461194452, 76.84544146786),
    Property(908629, 43.195739, 76.88641),
    Property(908626, 43.22507, 76.899426),
    Property(908624, 43.278657101984, 76.888983688342),
    Property(908619, 43.186819, 76.866099),
    Property(908616, 43.190211, 76.856155),
    Property(908615, 43.2316483656, 76.831370737865),
    Property(908602, 43.291717, 76.959632),
    Property(908601, 43.313925, 76.962183),
    Property(908599, 43.278039202595, 76.953108294546),
    Property(908598, 43.218431692805, 76.886331613581),
    Property(908583, 43.266524, 76.922785),
    Property(908579, 43.221569, 76.829888),
    Property(908577, 43.296461, 76.955185),
    Property(908575, 43.266829, 76.858329),
    Property(908574, 43.300431, 76.835395),
    Property(908573, 43.156675156095, 76.904156581595),
    Property(908572, 43.207732, 76.889231),
    Property(908569, 43.314051, 76.957662),
    Property(908568, 43.241987831969, 76.872232326543),
    Property(908566, 43.301216608084, 76.867377187042),
    Property(908565, 43.184979897364, 76.880004210538),
    Property(908555, 43.226438521119, 76.959328517283),
    Property(908554, 43.202093844471, 76.9469585678),
    Property(908542, 43.25397781409, 76.829919892504),
    Property(908538, 43.260929906792, 76.843794252377),
    Property(908527, 43.324262, 76.930364),
    Property(908520, 43.186846, 76.831819),
    Property(908508, 43.242493, 76.958898),
    Property(908490, 43.266403, 76.886931),
    Property(908488, 43.324452, 76.893183),
    Property(908485, 43.286481220636, 76.956698665717),
    Property(908484, 43.197677205217, 76.848945933832),
    Property(908482, 43.260041, 76.870357),
    Property(908480, 43.185853, 76.952203),
    Property(908479, 43.17373, 76.896291),
    Property(908478, 43.220779100142, 76.941615115046),
    Property(908476, 43.160744, 76.853621),
    Property(909046, 43.240066, 76.957691),
    Property(909045, 43.259593022654, 76.865124912119),
    Property(909044, 43.252298349321, 76.896736110508),
    Property(909043, 43.307818, 76.839554),
    Property(909038, 43.217535, 76.901798),
    Property(909030, 43.253634511489, 76.862957090234),
    Property(909027, 43.256860612814, 76.936333015203),
    Property(909024, 43.235193, 76.955616),
    Property(909022, 43.322124, 76.902499),
    Property(909020, 43.235971593367, 76.922157104076),
    Property(909019, 43.27296, 76.934416),
    Property(909015, 43.244945, 76.922468),
    Property(909009, 43.237754, 76.897477),
    Property(909001, 43.266130888735, 76.929350155078),
    Property(908989, 43.271175, 76.949049),
    Property(908987, 43.234593247147, 76.893238613311),
    Property(908983, 43.19957, 76.900792),
    Property(908981, 43.201844, 76.849094),
    Property(908980, 43.273152746575, 76.950253525042),
    Property(908976, 43.218724, 76.939366),
    Property(908975, 43.247881271427, 76.868811406082),
    Property(908974, 43.246981, 76.902589),
    Property(908973, 43.233609649091, 76.882290818256),
    Property(908968, 43.238089, 76.903729),
    Property(908966, 43.244768, 76.895815),
    Property(908965, 43.14535379194, 76.899784035421),
    Property(908963, 43.227458489673, 76.834178483013),
    Property(908962, 43.294906180115, 76.831057976591),
    Property(908961, 43.195009, 76.850684),
    Property(908960, 43.251473, 76.911356),
    Property(908958, 43.293935103445, 76.896205453679),
    Property(908955, 43.169033344865, 76.873052802102),
    Property(908954, 43.31285, 76.911769),
    Property(908949, 43.240243, 76.832457),
    Property(908944, 43.235666, 76.849669),
    Property(908940, 43.224492, 76.87219),
    Property(908934, 43.224834, 76.842033),
    Property(908915, 43.235048, 76.922468),
    Property(908890, 43.282566371221, 76.840127578799),
    Property(908884, 43.24924, 76.924912),
    Property(908867, 43.256588, 76.858014),
    Property(908858, 43.260258, 76.916459),
    Property(908836, 43.293407137637, 76.893225190072),
    Property(908825, 43.268024, 76.951744),
    Property(908820, 43.308147688846, 76.877957660854),
    Property(908818, 43.229071, 76.934533),
    Property(908799, 43.215440372548, 76.85440422222),
    Property(908769, 43.191171, 76.896444),
    Property(908768, 43.205682354427, 76.900377235364),
    Property(908766, 43.195193, 76.899193),
    Property(908759, 43.276518, 76.932619),
    Property(908755, 43.203158, 76.871785),
    Property(908754, 43.214552, 76.854798),
    Property(908753, 43.232414, 76.885718),
    Property(908742, 43.265556, 76.853208),
    Property(908740, 43.260973, 76.947298),
    Property(908739, 43.224873, 76.936841),
    Property(908738, 43.238095717421, 76.93657821726),
    Property(908737, 43.167867197258, 76.916281844148),
    Property(908736, 43.226673, 76.845222),
    Property(908730, 43.200711, 76.895782),
    Property(908728, 43.221076, 76.842617),
    Property(908726, 43.263343, 76.868929),
    Property(908725, 43.248452, 76.877184),
    Property(908724, 43.216149596656, 76.8755718725),
    Property(908663, 43.210461194452, 76.84544146786),
    Property(908629, 43.195739, 76.88641),
    Property(908626, 43.22507, 76.899426),
    Property(908624, 43.278657101984, 76.888983688342),
    Property(908619, 43.186819, 76.866099),
    Property(908616, 43.190211, 76.856155),
    Property(908615, 43.2316483656, 76.831370737865),
    Property(908602, 43.291717, 76.959632),
    Property(908601, 43.313925, 76.962183),
    Property(908599, 43.278039202595, 76.953108294546),
    Property(908598, 43.218431692805, 76.886331613581),
    Property(908583, 43.266524, 76.922785),
    Property(908579, 43.221569, 76.829888),
    Property(908577, 43.296461, 76.955185),
    Property(908575, 43.266829, 76.858329),
    Property(908574, 43.300431, 76.835395),
    Property(908573, 43.156675156095, 76.904156581595),
    Property(908572, 43.207732, 76.889231),
    Property(908569, 43.314051, 76.957662),
    Property(908568, 43.241987831969, 76.872232326543),
    Property(908566, 43.301216608084, 76.867377187042),
    Property(908565, 43.184979897364, 76.880004210538),
    Property(908555, 43.226438521119, 76.959328517283),
    Property(908554, 43.202093844471, 76.9469585678),
    Property(908542, 43.25397781409, 76.829919892504),
    Property(908538, 43.260929906792, 76.843794252377),
    Property(908527, 43.324262, 76.930364),
    Property(908520, 43.186846, 76.831819),
    Property(908508, 43.242493, 76.958898),
    Property(908490, 43.266403, 76.886931),
    Property(908488, 43.324452, 76.893183),
    Property(908485, 43.286481220636, 76.956698665717),
    Property(908484, 43.197677205217, 76.848945933832),
    Property(908482, 43.260041, 76.870357),
    Property(908480, 43.185853, 76.952203),
    Property(908479, 43.17373, 76.896291),
    Property(908478, 43.220779100142, 76.941615115046),
    Property(908476, 43.160744, 76.853621)
)

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

package com.example.kilt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.kilt.data.ActiveBusiness
import com.example.kilt.data.BathroomInsideItem
import com.example.kilt.data.BusinessCondition
import com.example.kilt.data.BusinessEntrance
import com.example.kilt.data.BusinessParking
import com.example.kilt.data.Communications
import com.example.kilt.data.Condition
import com.example.kilt.data.Conveniences
import com.example.kilt.data.DoorItem
import com.example.kilt.data.DrinkingWater
import com.example.kilt.data.Electricity
import com.example.kilt.data.FormerDormitoryItem
import com.example.kilt.data.FreePlanning
import com.example.kilt.data.HasRenters
import com.example.kilt.data.Heating
import com.example.kilt.data.InternetItem
import com.example.kilt.data.IrrigationWater
import com.example.kilt.data.IsBailedItem
import com.example.kilt.data.ListOfFurniture
import com.example.kilt.data.Listing
import com.example.kilt.data.NumRoom
import com.example.kilt.data.PropLabel
import com.example.kilt.data.SecurityItem
import com.example.kilt.data.Sewer
import com.example.kilt.data.Telephone
import com.example.kilt.data.TypesOfConstruction
import com.example.kilt.data.config.BalconyGlassItem
import com.example.kilt.data.config.BalconyItem
import com.example.kilt.data.config.BathroomItem
import com.example.kilt.data.config.Designations
import com.example.kilt.data.config.FloorMaterialItem
import com.example.kilt.data.config.Furnituries
import com.example.kilt.data.config.Houselines
import com.example.kilt.data.config.Locateds
import com.example.kilt.data.config.LoggiaItem
import com.example.kilt.data.config.NewBalconyItem
import com.example.kilt.data.config.NumOfFloor
import com.example.kilt.data.config.ParkingItem
import com.example.kilt.data.config.RentPeriodItem
import com.example.kilt.data.config.SuitsForItem
import com.example.kilt.data.config.ToiletSeparationItem
import com.example.kilt.data.config.WindowsItem
import com.example.kilt.network.RetrofitInstance
import com.example.kilt.network.SearchResponse
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.myapplication.data.HomeSale
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
//            TestFilterScreen()
//            KiltApp()
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
//            SearchPage(navController =

//            PropertyFilters()
            val homeSaleViewModel: HomeSaleViewModel = viewModel()
            homeSaleViewModel.loadHomeSale()
            val propLabels = homeSaleViewModel.config.value?.propLabels
            val probs = homeSaleViewModel.getListingProps()
            val scrollable = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollable)
                    .padding(top = 16.dp)
            ) {
                probs?.forEach { prop ->
                    val matchingLabel = propLabels?.find { it.property == prop }
                    when (matchingLabel?.filter_type) {
                        "list" -> {
                            TypeOfConstruction(
                                homeSaleViewModel = homeSaleViewModel,
                                prop = prop,
                                title = matchingLabel.label_ru
                            )
                        }
                        "range" -> RangeFilter(matchingLabel.label_ru)
                        else -> {}
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun  TypeOfConstruction(
    homeSaleViewModel: HomeSaleViewModel,
    prop: String,
    title: String
) {
    homeSaleViewModel.loadHomeSale()
    val filters = when (prop) {
        "floor" -> homeSaleViewModel.config.value?.propMapping?.floor?.list
        "num_rooms" -> homeSaleViewModel.config.value?.propMapping?.num_rooms?.list
        "furniture_list" -> homeSaleViewModel.config.value?.propMapping?.furniture_list?.list
        "new_conveniences" -> homeSaleViewModel.config.value?.propMapping?.new_conveniences?.list
        "rent_period" -> homeSaleViewModel.config.value?.propMapping?.rent_period?.list
        "bathroom_inside"-> homeSaleViewModel.config.value?.propMapping?.bathroom_inside?.list
        "security"->homeSaleViewModel.config.value?.propMapping?.security?.list
        "new_balcony"->homeSaleViewModel.config.value?.propMapping?.new_balcony?.list
        "toilet_separation"->homeSaleViewModel.config.value?.propMapping?.toilet_separation?.list
        "loggia"->homeSaleViewModel.config.value?.propMapping?.loggia?.list
        "windows"->homeSaleViewModel.config.value?.propMapping?.windows?.list
        "suits_for"->homeSaleViewModel.config.value?.propMapping?.suits_for?.list
        "construction_type"->homeSaleViewModel.config.value?.propMapping?.construction_type?.list
        "furniture" ->homeSaleViewModel.config.value?.propMapping?.furniture?.list
        "former_dormitory" ->homeSaleViewModel.config.value?.propMapping?.former_dormitory?.list
        "internet" ->homeSaleViewModel.config.value?.propMapping?.internet?.list
        "balcony_glass" ->homeSaleViewModel.config.value?.propMapping?.balcony_glass?.list
        "door" ->homeSaleViewModel.config.value?.propMapping?.door?.list
        "parking" ->homeSaleViewModel.config.value?.propMapping?.parking?.list
        "floor_material" ->homeSaleViewModel.config.value?.propMapping?.floor_material?.list
        "is_bailed" ->homeSaleViewModel.config.value?.propMapping?.is_bailed?.list
        "bathroom" ->homeSaleViewModel.config.value?.propMapping?.bathroom?.list
        "balcony" ->homeSaleViewModel.config.value?.propMapping?.balcony?.list
        "condition" ->homeSaleViewModel.config.value?.propMapping?.condition?.list
        "heating" ->homeSaleViewModel.config.value?.propMapping?.heating?.list
        "sewer" ->homeSaleViewModel.config.value?.propMapping?.sewer?.list
        "drinking_water" ->homeSaleViewModel.config.value?.propMapping?.drinking_water?.list
        "irrigation_water" ->homeSaleViewModel.config.value?.propMapping?.irrigation_water?.list
        "electricity" ->homeSaleViewModel.config.value?.propMapping?.electricity?.list
        "telephone" ->homeSaleViewModel.config.value?.propMapping?.telephone?.list

        "designation" ->homeSaleViewModel.config.value?.propMapping?.designation?.list
        "where_located"->homeSaleViewModel.config.value?.propMapping?.where_located?.list
        "active_business" ->homeSaleViewModel.config.value?.propMapping?.active_business?.list
        "has_renters" ->homeSaleViewModel.config.value?.propMapping?.has_renters?.list
        "free_planning" ->homeSaleViewModel.config.value?.propMapping?.free_planning?.list
        "business_condition" ->homeSaleViewModel.config.value?.propMapping?.business_condition?.list
        "business_parking" ->homeSaleViewModel.config.value?.propMapping?.business_parking?.list
        "business_entrance" ->homeSaleViewModel.config.value?.propMapping?.business_entrance?.list
        "communications" ->homeSaleViewModel.config.value?.propMapping?.communications?.list
        "line_of_houses" ->homeSaleViewModel.config.value?.propMapping?.communications?.list
        else -> null
    }
    TypeFilterButtons(
        filters = filters,
        getId = { (it as? Any)?.let { item ->
            when (item) {
                is NumOfFloor -> item.id.toString()
                is NumRoom -> item.id.toString()
                is ListOfFurniture -> item.id.toString()
                is Conveniences -> item.id.toString()
                is RentPeriodItem -> item.id.toString()
                is BathroomInsideItem -> item.id.toString()
                is SecurityItem -> item.id.toString()
                is NewBalconyItem -> item.id.toString()
                is ToiletSeparationItem -> item.id.toString()
                is LoggiaItem ->item.id.toString()
                is WindowsItem -> item.id.toString()
                is SuitsForItem -> item.id.toString()
                is TypesOfConstruction -> item.id.toString()
                is Furnituries -> item.id.toString()
                is FormerDormitoryItem -> item.id.toString()
                is InternetItem -> item.id.toString()
                is BalconyGlassItem -> item.id.toString()
                is DoorItem -> item.id.toString()
                is ParkingItem -> item.id.toString()
                is FloorMaterialItem -> item.id.toString()
                is IsBailedItem -> item.id.toString()
                is BathroomItem ->item.id.toString()
                is BalconyItem ->item.id.toString()
                is Condition ->item.id.toString()
                is Heating ->item.id.toString()
                is Sewer->item.id.toString()
                is DrinkingWater ->item.id.toString()
                is IrrigationWater ->item.id.toString()
                is Electricity ->item.id.toString()
                is Telephone ->item.id.toString()
                is Designations ->item.id.toString()
                is Locateds ->item.id.toString()
                is ActiveBusiness->item.id.toString()
                is FreePlanning ->item.id.toString()
                is BusinessCondition ->item.id.toString()
                is BusinessParking ->item.id.toString()
                is BusinessEntrance ->item.id.toString()
                is Communications ->item.id.toString()
                is Houselines -> item.id.toString()
                is HasRenters ->item.id.toString()
                else -> item.toString()
            }
        } ?: "" },
        getName = { (it as? Any)?.let { item ->
            when (item) {
                is NumOfFloor -> item.name.toString()
                is NumRoom -> item.name.toString()
                is ListOfFurniture -> item.name
                is Conveniences -> item.name
                is RentPeriodItem -> item.name
                is BathroomInsideItem -> item.name
                is SecurityItem -> item.name
                is NewBalconyItem -> item.name
                is ToiletSeparationItem -> item.name
                is LoggiaItem ->item.name
                is WindowsItem -> item.name
                is SuitsForItem -> item.name
                is TypesOfConstruction -> item.name
                is Furnituries -> item.name
                is FormerDormitoryItem -> item.name
                is InternetItem -> item.name
                is BalconyGlassItem -> item.name
                is DoorItem -> item.name
                is ParkingItem -> item.name
                is FloorMaterialItem -> item.name
                is IsBailedItem -> item.name
                is BathroomItem ->item.name
                is BalconyItem ->item.name
                is Condition ->item.name
                is Heating ->item.name
                is Sewer->item.name
                is DrinkingWater ->item.name
                is IrrigationWater ->item.name
                is Electricity ->item.name
                is Telephone ->item.name
                is Designations ->item.name
                is Locateds ->item.name
                is ActiveBusiness->item.name
                is FreePlanning ->item.name
                is BusinessCondition ->item.name
                is BusinessParking ->item.name
                is BusinessEntrance ->item.name
                is Communications ->item.name
                is Houselines -> item.name
                is HasRenters ->item.name
                else -> item.toString()
            }
        } ?: "" },
        title = title,
        onFilterSelected = { selectedFilters ->
            println("Выбраны фильтры: $selectedFilters")
        }
    )
}

@Composable
fun <T> TypeFilterButtons(
    filters: List<T>?,
    getId: (T) -> String,
    getName: (T) -> String,
    title: String,
    onFilterSelected: (List<String>) -> Unit
) {
    var selectedFilters by remember { mutableStateOf<List<String>>(emptyList()) }

    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(top = 8.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            color = Color(0xff010101)
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ConstructionTypeFilterButton(
                text = "Все",
                isSelected = selectedFilters.isEmpty(),
                onClick = {
                    selectedFilters = emptyList()
                    onFilterSelected(selectedFilters)
                }
            )
        }
        items(filters ?: emptyList()) { filter ->
            ConstructionTypeFilterButton(
                text = getName(filter),
                isSelected = selectedFilters.contains(getId(filter)),
                onClick = {
                    selectedFilters = if (selectedFilters.contains(getId(filter))) {
                        selectedFilters - getId(filter)
                    } else {
                        selectedFilters + getId(filter)
                    }
                    onFilterSelected(selectedFilters)
                }
            )
        }
    }
}

@Composable
fun ConstructionTypeFilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .widthIn(35.dp)
            .wrapContentWidth()
            .border(
                width = 1.5.dp,
                color = if (isSelected) Color.Blue else Color(0xffc2c2d6),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Color.Blue.copy(alpha = 0.1f) else Color(0xffFFFFFF),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (isSelected) Color.Blue else Color(0xff110D28),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .widthIn(35.dp)
                .align(alignment = Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun RangeFilter(title: String) {
    Column {
        Text(title)
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            var minValue by remember { mutableStateOf("") }
            var maxValue by remember { mutableStateOf("") }

            TextField(
                value = minValue,
                onValueChange = { minValue = it },
                label = { Text("Min") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = maxValue,
                onValueChange = { maxValue = it },
                label = { Text("Max") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
@Composable
fun PropertyFilters() {
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    homeSaleViewModel.loadHomeSale()
    val propLabels = homeSaleViewModel.config.value?.propLabels
    val filters = homeSaleViewModel.config.value?.propMapping?.num_rooms?.list

    LaunchedEffect(Unit) {
        homeSaleViewModel.loadHomeSale()
    }

    Log.d("probLabels", "PropertyFilters: ${propLabels?.size}")

    Column {
        propLabels?.forEach { propLabel ->
            when (propLabel.filter_type) {
                "range" -> RangeFilter(propLabel)
                "list" -> {
                    val listItems = getListForProperty(propLabel.property, filters)
                    if (listItems != null && listItems.isNotEmpty()) {
                        ListFilter(propLabel, listItems)
                    }
                }

                "search" -> SearchFilter(propLabel)
                else -> ToggleFilter(propLabel)
            }
        }
    }
}

@Composable
fun RangeFilter(propLabel: PropLabel) {
    var minValue by remember { mutableStateOf("") }
    var maxValue by remember { mutableStateOf("") }

    Column {
        Text(propLabel.label_ru)
        Row {
            OutlinedTextField(
                value = minValue,
                onValueChange = { minValue = it },
                label = { Text("От") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = maxValue,
                onValueChange = { maxValue = it },
                label = { Text("До") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ListFilter(propLabel: PropLabel, items: List<NumRoom>) {
    Column {
        Text(propLabel.label_ru)
        LazyRow {
            items(items) { item ->
                OutlinedButton(
                    onClick = { /* Handle selection */ },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(item.name.toString())
                }
            }
        }
    }
}

@Composable
fun SearchFilter(propLabel: PropLabel) {
    var searchQuery by remember { mutableStateOf("") }

    Column {
        Text(propLabel.label_ru)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск") }
        )
    }
}

@Composable
fun ToggleFilter(propLabel: PropLabel) {
    var isChecked by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(propLabel.label_ru)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )
    }
}

fun getListForProperty(property: String?, numRooms: List<NumRoom>?): List<NumRoom>? {
    return when (property) {
        "num_rooms" -> numRooms
        // Add other properties here if needed
        else -> null
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

















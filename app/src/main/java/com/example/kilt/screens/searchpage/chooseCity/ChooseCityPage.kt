@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.kilt.screens.searchpage.chooseCity

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.custom.CustomToggleButton
import com.example.kilt.data.kato.District
import com.example.kilt.data.kato.MicroDistrict
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.utills.LockScreenOrientation
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCityPage(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    viewModel: ChooseCityViewModel,
    modifier: Modifier
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val selectedCity by viewModel.selectedCity
    val selectCity by viewModel.selectCity
    val districts by viewModel.districts
    val currentScreen by viewModel.currentScreen
    val isRentSelected by viewModel.isRentSelected
    val isBuySelected by viewModel.isBuySelected
    val expandedDistricts = viewModel.expandedDistricts
    val residentialComplexes by viewModel.residentialComplexes
    val searchQuery by viewModel.searchQuery
    val cities = listOf(
        "г.Алматы",
        "г.Астана",
        "г.Шымкент",
        "Алматинская область"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (currentScreen == 0) {
                        navController.popBackStack()
                    } else {
                        viewModel.goBack()
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xff566982)
                    )
                }
                TextButton(onClick = {
                    viewModel.resetSelection()
                    searchViewModel.clearAllFilters()
                }) {
                    Text(
                        text = "Сбросить",
                        fontSize = 16.sp,
                        color = Color(0xff566982),
                        fontWeight = FontWeight.W400
                    )
                }
            }
            if (selectedCity != null) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(15.dp))
                        .background(Color(0xffF2F2F2))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomToggleButton(
                        text = "Район",
                        isSelected = isRentSelected,
                        onClick = { viewModel.toggleRentBuySelection(true) },
                        modifier = Modifier.weight(1f)
                    )
                    CustomToggleButton(
                        text = "ЖК",
                        isSelected = isBuySelected,
                        onClick = { viewModel.toggleRentBuySelection(false) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            val city = when (selectedCity) {
                null -> {
                    "Город"
                }
                "Алматинская область" -> {
                    "Алматинская область"
                }
                else -> {
                    selectedCity
                }
            }
            Text(
                text = city.toString(),
                color = Color(0xff010101),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
            )
            if (isBuySelected) {
                TextField(
                    value = searchQuery,
                    onValueChange = { query -> viewModel.updateSearchQuery(query) },
                    placeholder = { Text(text = "Название ЖК", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .height(50.dp)
                        .heightIn(min = 50.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )
            }
            when (currentScreen) {
                0 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(cities.size) { index ->
                            CityRow(cityName = cities[index], onCityClick = { city ->
                                viewModel.selectCity(city)
                            })
                        }
                    }
                }

                1 -> {
                    if (isRentSelected) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.all_districk_icon),
                                        contentDescription = "Location",
                                        tint = Color(0xff566982)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Выбрать весь город",
                                        fontSize = 16.sp,
                                        color = Color(0xff010101),
                                        fontWeight = FontWeight.W700
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    var isChecked by remember { mutableStateOf(false) }
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { checked ->
                                            isChecked = checked
                                            if (checked) {
                                                selectedCity?.let { city ->
                                                    val cityId = when (city) {
                                                        "г.Алматы" -> "75000000"
                                                        "г.Астана" -> "710000000"
                                                        "г.Шымкент" -> "79000000"
                                                        "Алматинская область" -> "19000000"
                                                        else -> null
                                                    }
                                                    cityId?.let { id ->
                                                        viewModel.selectCity(city)
                                                        searchViewModel.updateListFilter1(
                                                            "kato_path",
                                                            listOf(id)
                                                        )
                                                    }
                                                }
                                            } else {
                                                searchViewModel.updateListFilter1(
                                                    "kato_path",
                                                    emptyList()
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            items(districts?.size ?: 0) { index ->
                                val district = districts!![index]
                                val isExpanded = expandedDistricts.contains(district)
                                DistrictRow(
                                    district = district,
                                    microDistricts = viewModel.microDistrictsByDistrict[district.id]
                                        ?: emptyList(),
                                    isExpanded = isExpanded,
                                    onExpandClick = {
                                        viewModel.toggleDistrictExpansion(district)
                                        if (!isExpanded) {
                                            viewModel.selectDistrict(district)
                                        }
                                    },
                                    searchViewModel,
                                    selectedCity = selectedCity.toString(),
                                    chooseCityViewModel = viewModel,
                                )
                            }
                        }
                    } else if (isBuySelected) {
                        val filteredComplexes = residentialComplexes.filter {
                            it.residential_complex_name.contains(searchQuery, ignoreCase = true)
                        }
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(filteredComplexes.size) { index ->
                                val residentialComplex = filteredComplexes[index]
                                ResidentialComplexRow(
                                    complex = residentialComplex,
                                    searchViewModel,
                                    chooseCityViewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                navController.popBackStack()
                searchViewModel.performSearch()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(
                text = "Применить",
                color = Color.White,
            )
        }
    }
}



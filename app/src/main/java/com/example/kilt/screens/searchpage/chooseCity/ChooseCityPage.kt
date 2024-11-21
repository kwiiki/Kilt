@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.kilt.screens.searchpage.chooseCity

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.presentation.custom.CustomToggleButton
import com.example.kilt.utills.LockScreenOrientation
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun ChooseCityPage(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    viewModel: ChooseCityViewModel,
    modifier: Modifier
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val selectedCity by viewModel.selectedCity
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
            TopAppBarChooseCityPage(
                chooseCityViewModel = viewModel,
                navController,
                currentScreen,
                modifier
            )
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
                                ChooseAllCityRow(searchViewModel = searchViewModel, chooseCityViewModel = viewModel,selectedCity)
                            }
                            items(districts?.size ?: 0) { index ->
                                val district = districts!![index]
                                val isExpanded = expandedDistricts.contains(district)
                                DistrictRow(
                                    district = district,
                                    microDistricts = viewModel.microDistrictsByDistrict[district.id]
                                        ?: emptyList(),
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
                searchViewModel.performSearch()
                navController.popBackStack()
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



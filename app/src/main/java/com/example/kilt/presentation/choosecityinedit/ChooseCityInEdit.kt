package com.example.kilt.presentation.choosecityinedit

import android.content.pm.ActivityInfo
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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kilt.R
import com.example.kilt.domain.choosecity.model.MicroDistrict
import com.example.kilt.models.kato.District
import com.example.kilt.presentation.choosecityinedit.viewmodel.ChooseCityInEditViewModel
import com.example.kilt.screens.searchpage.chooseCity.CityRow
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.utills.LockScreenOrientation

@Composable
fun ChooseCityInEdit(
    navController: NavController,
    chooseCityInEditViewModel: ChooseCityInEditViewModel
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val selectedCity by chooseCityInEditViewModel.selectedCity
    val currentScreen by chooseCityInEditViewModel.currentScreen
    val districts by chooseCityInEditViewModel.districts

    val cities = listOf(
        "г.Алматы",
        "г.Астана",
        "г.Шымкент",
        "Алматинская область"
    )
    val loadingDistrict = chooseCityInEditViewModel.loadingDistrict.value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (currentScreen == 0) {
                        navController.popBackStack()
                    } else {
                        chooseCityInEditViewModel.goBack()
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xff566982)
                    )
                }
                TextButton(onClick = {
                    chooseCityInEditViewModel.resetSelection()
                }) {
                    Text(
                        text = "Сбросить",
                        fontSize = 16.sp,
                        color = Color(0xff566982),
                        fontWeight = FontWeight.W400
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
            when (currentScreen) {
                0 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(cities.size) { index ->
                            CityRow(cityName = cities[index], onCityClick = { city ->
                                chooseCityInEditViewModel.selectCity(city)
                                chooseCityInEditViewModel.selectCites(city)

                            })
                        }
                    }
                }
                1 -> {
                    if (loadingDistrict) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                color = Color.Gray,
                                strokeWidth = 3.dp,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = 8.dp)
                            )
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.all_districk_icon),
                                        contentDescription = "Location",
                                        tint = Color(0xff566982)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Выбрать весь город",
                                        fontSize = 16.sp,
                                        color = Color(0xff010101),
                                        fontWeight = FontWeight.W700
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Checkbox(
                                        checked = chooseCityInEditViewModel.selectAllInCity[selectedCity] == true,
                                        onCheckedChange = {
                                            chooseCityInEditViewModel.toggleSelectAllInCity(selectedCity!!)
                                            chooseCityInEditViewModel.selectCites(selectedCity!!)
                                        },
                                        enabled = chooseCityInEditViewModel.selectAllInCity[selectedCity] == true || chooseCityInEditViewModel.selectAllInCity.all { it.value == false },
                                        colors = CheckboxDefaults.colors(
                                            uncheckedColor = Color.Black,
                                            checkmarkColor = Color.White,
                                            checkedColor = Color(0xff3F4FE0)
                                        )
                                    )
                                }
                                CustomDivider()
                            }
                            items(districts?.size ?: 0) { index ->
                                val district = districts!![index]
                                District(
                                    district = district,
                                    chooseCityInEditViewModel,
                                    selectedCity = selectedCity.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                chooseCityInEditViewModel.applySelection()
                navController.popBackStack()
                chooseCityInEditViewModel.resetSelection()
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

@Composable
fun District(
    district: District,
    chooseCityInEditViewModel: ChooseCityInEditViewModel,
    selectedCity: String
) {
    var isExpanded by remember { mutableStateOf(false) }
    val districtMicroDistricts = chooseCityInEditViewModel.microDistrictsByDistrict[district.id] ?: emptyList()
    val isLoading = chooseCityInEditViewModel.loadingDistricts[district.id] == true
    val selectAllInDistrict = chooseCityInEditViewModel.selectAllInDistrict[district.id] ?: false
    val isCitySelected = chooseCityInEditViewModel.selectAllInCity[selectedCity] == true
    val isEnabled = !isCitySelected && !chooseCityInEditViewModel.isAnyCitySelected()

    val isOtherDistrictSelected = chooseCityInEditViewModel.districts.value!!
        .any { districtItem ->
            districtItem.id != district.id && chooseCityInEditViewModel.isDistrictSelected(
                districtItem.id
            )
        }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
                if (isExpanded && districtMicroDistricts.isEmpty()) {
                    chooseCityInEditViewModel.loadMicroDistricts(district.id)
                }
                chooseCityInEditViewModel.selectDistrict(district.name)
            }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedCity != "Алматинская область") {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.pin_icon),
                contentDescription = "Location",
                tint = Color(0xff566982)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = district.name,
            fontSize = 16.sp,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Expand/Collapse",
            modifier = Modifier.size(30.dp),
            tint = Color(0xff566982)
        )
    }
    if (isExpanded) {
        if (isLoading) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(top = 8.dp)
                )
            }
        } else {
            CustomDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = isEnabled) {
                        chooseCityInEditViewModel.toggleSelectAllInDistrict(
                            district.id,
                            district.name
                        )
                    }
                    .padding(vertical = 8.dp)
                    .padding(start = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.all_districk_icon),
                    contentDescription = "Location",
                    tint = Color(0xff566982)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Выбрать весь район",
                    fontSize = 16.sp,
                    color = Color(0xff010101),
                    fontWeight = FontWeight.W700
                )
                Spacer(modifier = Modifier.weight(1f))

                Checkbox(
                    checked = selectAllInDistrict,
                    onCheckedChange = {
                        chooseCityInEditViewModel.toggleSelectAllInDistrict(
                            district.id,
                            district.name
                        )
                        chooseCityInEditViewModel.selectDistrict(district.name)
                    },
                    enabled = isEnabled ,
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White,
                        checkedColor = Color(0xff3F4FE0)
                    )
                )
            }
            CustomDivider()
            districtMicroDistricts.forEach { microDistrict ->
                MicroDistrict(
                    microDistrict = microDistrict,
                    chooseCityInEditViewModel = chooseCityInEditViewModel,
                    isEnabled = !selectAllInDistrict && !isOtherDistrictSelected && isEnabled
                )
            }
        }
    }
    CustomDivider()
}

@Composable
fun MicroDistrict(
    microDistrict: MicroDistrict,
    chooseCityInEditViewModel: ChooseCityInEditViewModel,
    isEnabled: Boolean
) {
    val isSelected = chooseCityInEditViewModel.selectedMicroDistrict.value == microDistrict
    val isCitySelected = chooseCityInEditViewModel.selectAllInCity[chooseCityInEditViewModel.selectedCity.value] == true

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled && !isCitySelected) {
                chooseCityInEditViewModel.selectMicroDistrict(microDistrict)
            }
            .padding(vertical = 10.dp)
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.pin_icon),
            contentDescription = "Location",
            tint = if (isEnabled) Color(0xff566982) else Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = microDistrict.name,
            fontSize = 16.sp,
            color = if (isEnabled) Color(0xff010101) else Color.Gray,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                chooseCityInEditViewModel.selectMicroDistrict(microDistrict)
                chooseCityInEditViewModel.selectMicroDistricts(microDistrict)
            },
            enabled = isEnabled && !isCitySelected,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White,
                checkedColor = Color(0xff3F4FE0)
            ),
        )
    }
    CustomDivider()
}


package com.example.kilt.screens.searchpage.chooseCity

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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.data.kato.District
import com.example.kilt.data.kato.MicroDistrict
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.viewmodels.ChooseCityViewModel


@Composable
fun ChooseCityPage(
    navController: NavHostController,
    viewModel: ChooseCityViewModel = hiltViewModel()
) {

    val selectedCity by viewModel.selectedCity
    val districts by viewModel.districts
    val microdistricts by viewModel.microdistricts
    val currentScreen by viewModel.currentScreen

    val cities = listOf(
        "г.Алматы",
        "г.Астана",
        "г.Шымкент",
        "Алматинская область"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Верхняя строка с иконкой назад и кнопкой "Сбросить"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
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
                }) {
                    Text(
                        text = "Сбросить",
                        fontSize = 16.sp,
                        color = Color(0xff566982),
                        fontWeight = FontWeight.W400
                    )
                }
            }

            // Отображение списка в зависимости от текущего экрана
            when (currentScreen) {
                0 -> {  // Список городов
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(cities.size) { index ->
                            CityRow(cityName = cities[index], onCityClick = { city ->
                                viewModel.selectCity(city)
                            })
                        }
                    }
                }
                1 -> {  // Список районов
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(districts?.size ?: 0) { index ->
                            DistrictRow(
                                district = districts!![index],
                                onDistrictClick = { district ->
                                    viewModel.selectDistrict(district)
                                }
                            )
                        }
                    }
                }
                2 -> {  // Список микрорайонов
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(microdistricts?.size ?: 0) { index ->
                            MicroDistrictRow(
                                microDistrict = microdistricts!![index],
                                isChecked = false,  // Здесь можно настроить логику выбора
                                onDistrictClick = { microDistrict ->
                                    viewModel.selectMicroDistrict(microDistrict)
                                }
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                // Логика применения выбора
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
fun CityRow(cityName: String, onCityClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCityClick(cityName) }
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.pin_icon),
            contentDescription = "Location",
            tint = Color(0xff566982)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = cityName,
            fontSize = 16.sp,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow",
            modifier = Modifier.size(30.dp),
            tint = Color(0xff566982)
        )
    }
    CustomDivider()
}

@Composable
fun DistrictRow(
    district: District,
    onDistrictClick: (District) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDistrictClick(district) }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = district.name,
            fontSize = 16.sp,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Default.KeyboardArrowDown,
            contentDescription = "Arrow",
            modifier = Modifier.size(30.dp),
            tint = Color(0xff566982)
        )

    }
    CustomDivider()
}

@Composable
fun MicroDistrictRow(
    microDistrict: MicroDistrict,
    isChecked: Boolean,
    onDistrictClick: (MicroDistrict) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDistrictClick(microDistrict) }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.pin_icon),
            contentDescription = "Location",
            tint = Color(0xff566982)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = microDistrict.name,
            fontSize = 16.sp,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onDistrictClick(microDistrict) }
        )
    }
    CustomDivider()
}
@Composable
@Preview(showBackground = true)
fun PreviewChooseCityPage() {
    val navController = rememberNavController()
    ChooseCityPage(navController = navController)
}
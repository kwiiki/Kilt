package com.example.kilt.screens.searchpage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.navigation.NavPath
import com.example.kilt.viewmodels.ChooseCityViewModel


@Composable
fun SearchBar(
    chooseCityViewModel: ChooseCityViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.padding()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(45.dp)
                .clickable { navController.navigate(NavPath.CHOOSECITYPAGE.name) }
                .background(Color.Transparent, shape = RoundedCornerShape(14.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFFDBDFE4),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            val selectedResidentialComplex = chooseCityViewModel.selectedComplexNames.joinToString(" / ") { it }
            val selectedCity = chooseCityViewModel.selectCity.value
            val selectedDistrict = chooseCityViewModel.selectedDistrict.value?.name
            Log.d("selectedDistrict", "SearchBar: $selectedDistrict")
            val selectedMicroDistricts = chooseCityViewModel.selectedMicroDistricts
                .joinToString(" / ") { it.name }
            val locationText = when {
                selectedMicroDistricts.isNotEmpty() -> selectedMicroDistricts
                selectedDistrict != null -> selectedDistrict
                selectedCity != null -> selectedCity
                selectedResidentialComplex.isNotEmpty() -> selectedResidentialComplex
                else -> "Город, район, ЖК"
            }
            val font = FontWeight.W600
            val defaultFont = FontWeight.W300

            Text(
                text = locationText,
                color = if (locationText != "Город, район, ЖК") Color(0xff010101) else Color(
                    0xff566982
                ),
                fontSize = 16.sp,
                fontWeight = if (locationText != "Город, район, ЖК") font else defaultFont,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearchBar() {
    val navController = rememberNavController()
    SearchBar(chooseCityViewModel = hiltViewModel(), navController = navController)

}
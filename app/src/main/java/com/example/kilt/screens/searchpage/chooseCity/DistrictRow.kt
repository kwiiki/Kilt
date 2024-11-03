package com.example.kilt.screens.searchpage.chooseCity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.example.kilt.R
import com.example.kilt.models.kato.District
import com.example.kilt.models.kato.MicroDistrict
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun DistrictRow(
    district: District,
    microDistricts: List<MicroDistrict>,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    searchViewModel: SearchViewModel,
    selectedCity: String,
    chooseCityViewModel: ChooseCityViewModel
) {

    var isDistrictChecked by remember { mutableStateOf(false) }
    val isExpanded by remember {
        derivedStateOf {
            chooseCityViewModel.expandedDistrictsMap[district.id] ?: false
        }
    }
    val isLoading by remember {
        derivedStateOf {
            chooseCityViewModel.loadingDistricts[district.id] ?: false
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { chooseCityViewModel.toggleDistrictExpansion(district) }
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
        CustomDivider()
        if (isExpanded) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )
            } else {
                ChooseAllDistrictRow(
                    district,
                    selectedCity,
                    searchViewModel = searchViewModel,
                    chooseCityViewModel = chooseCityViewModel
                )
                CustomDivider()
                val districtMicroDistricts =
                    chooseCityViewModel.microDistrictsByDistrict[district.id] ?: emptyList()
                districtMicroDistricts.forEach { microDistrict ->
                    MicroDistrictRow(
                        microDistrict = microDistrict,
                        district = district,
                        isDistrictChecked = isDistrictChecked,
                        selectedCity = selectedCity,
                        searchViewModel = searchViewModel,
                        chooseCityViewModel = chooseCityViewModel
                    )
                }
            }
        }
    }
}

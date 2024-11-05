package com.example.kilt.screens.searchpage.chooseCity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun ChooseAllDistrictRow(
    district: District,
    selectedCity: String?,
    searchViewModel: SearchViewModel,
    chooseCityViewModel: ChooseCityViewModel
) {
    // Use the ViewModel's map to track the district checkbox state
    val isDistrictChecked = chooseCityViewModel.isCheckMapDistrict[district.id] ?: false
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                chooseCityViewModel.setIsCheckDistrict(district.id, !isDistrictChecked)
                handleDistrictSelection(
                    !isDistrictChecked,
                    district,
                    selectedCity,
                    searchViewModel,
                    chooseCityViewModel
                )
            }
            .padding(vertical = 12.dp, horizontal = 16.dp),
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
            checked = isDistrictChecked,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White,
                checkedColor = Color(0xff3F4FE0)
            ),
            onCheckedChange = { checked ->
                chooseCityViewModel.setIsCheckDistrict(district.id, checked)
                handleDistrictSelection(
                    checked,
                    district,
                    selectedCity,
                    searchViewModel,
                    chooseCityViewModel
                )
            }
        )
    }
}

private fun handleDistrictSelection(
    isChecked: Boolean,
    district: District,
    selectedCity: String?,
    searchViewModel: SearchViewModel,
    chooseCityViewModel: ChooseCityViewModel
) {
    selectedCity?.let { city ->
        if (isChecked) {
            val cityId = when (city) {
                "г.Алматы" -> "750000000"
                "г.Астана" -> "710000000"
                "г.Шымкент" -> "790000000"
                "Алматинская область" -> "190000000"
                else -> null
            }
            cityId?.let { id ->
                val katoPath = "$id,${district.id}"
                searchViewModel.updateListFilter1("kato_path", listOf(katoPath))
                chooseCityViewModel.selectDistrictForTextView(district.name)
            }
        } else {
            searchViewModel.updateListFilter1("kato_path", emptyList())
            chooseCityViewModel.removeDistrictForTextView(district.name)
        }
    }
}
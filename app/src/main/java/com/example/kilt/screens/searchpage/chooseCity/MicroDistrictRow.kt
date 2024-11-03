package com.example.kilt.screens.searchpage.chooseCity

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
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
import com.example.kilt.models.kato.MicroDistrict
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun MicroDistrictRow(
    microDistrict: MicroDistrict,
    isDistrictChecked: Boolean,
    district: District,
    selectedCity: String,
    searchViewModel: SearchViewModel,
    chooseCityViewModel: ChooseCityViewModel
) {
    val isCheckedState = chooseCityViewModel.selectedMicroDistricts.contains(microDistrict)
    Log.d("checkState", "MicroDistrictRow: $isCheckedState")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isDistrictChecked) {
                val newCheckedState = !isCheckedState
                chooseCityViewModel.toggleMicroDistrictSelection(microDistrict, newCheckedState)
                updateKatoPath(
                    newCheckedState,
                    selectedCity,
                    district,
                    microDistrict,
                    searchViewModel,
                    chooseCityViewModel
                )
            }
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
            checked = isCheckedState,
            onCheckedChange = { checked ->
                chooseCityViewModel.toggleMicroDistrictSelection(microDistrict, checked)
                updateKatoPath(
                    checked,
                    selectedCity,
                    district,
                    microDistrict,
                    searchViewModel,
                    chooseCityViewModel
                )
            },
            enabled = !isDistrictChecked
        )
    }
    CustomDivider()
}
private fun updateKatoPath(
    isChecked: Boolean,
    selectedCity: String,
    district: District,
    microDistrict: MicroDistrict,
    searchViewModel: SearchViewModel,
    chooseCityViewModel: ChooseCityViewModel
) {
    chooseCityViewModel.toggleMicroDistrictSelection(microDistrict, isChecked)
    val cityId = when (selectedCity) {
        "г.Алматы" -> "750000000"
        "г.Астана" -> "710000000"
        "г.Шымкент" -> "790000000"
        "Алматинская область" -> "190000000"
        else -> null
    }
    cityId?.let {
        val katoPath = "$cityId,${district.id},${microDistrict.id}"
        val newList = chooseCityViewModel.addOrRemoveKatoPath(katoPath, isChecked)
        searchViewModel.updateListFilter1("kato_path", newList)
    }
}
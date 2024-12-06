package com.example.kilt.screens.searchpage.chooseCity

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
import com.example.kilt.presentation.choosecity.viewmodel.ChooseCityViewModel
import com.example.kilt.presentation.search.viewmodel.SearchViewModel

@Composable
fun ChooseAllCityRow(
    searchViewModel: SearchViewModel,
    chooseCityViewModel: ChooseCityViewModel,
    selectedCity: String?
) {
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
        Checkbox(
            checked = chooseCityViewModel.isCheckMap[selectedCity] ?: false,
            onCheckedChange = { checked ->
                selectedCity?.let { city ->
                    chooseCityViewModel.setIsCheck(city, checked)
                    if (checked) {
                        val cityId = when (city) {
                            "г.Алматы" -> "75000000"
                            "г.Астана" -> "710000000"
                            "г.Шымкент" -> "79000000"
                            "Алматинская область" -> "19000000"
                            else -> null
                        }
                        cityId?.let { id ->
                            chooseCityViewModel.selectCityForTextView(city)
                            searchViewModel.updateListFilter1(
                                "kato_path",
                                listOf(id)
                            )
                        }
                    } else {
                        searchViewModel.updateListFilter1(
                            "kato_path",
                            emptyList()
                        )
                        chooseCityViewModel.removeCityForTextView()
                    }
                }
            }
        )
    }
}
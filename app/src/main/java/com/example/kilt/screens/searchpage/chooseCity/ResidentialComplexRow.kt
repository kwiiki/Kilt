package com.example.kilt.screens.searchpage.chooseCity

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.R
import com.example.kilt.models.kato.ResidentialComplex
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.presentation.choosecity.viewmodel.ChooseCityViewModel
import com.example.kilt.presentation.search.viewmodel.SearchViewModel

@Composable
fun ResidentialComplexRow(
    complex: ResidentialComplex,
    searchViewModel: SearchViewModel,
    chooseCityViewModel: ChooseCityViewModel
) {
    val isChecked = remember { mutableStateOf(chooseCityViewModel.isComplexSelected(complex.id)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.unselected_builds_icon),
            contentDescription = "Location",
            tint = Color(0xff566982)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = complex.residential_complex_name,
            fontSize = 16.sp,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { checked ->
                isChecked.value = checked
                if (checked) {
                    chooseCityViewModel.addComplexId(complex.id)
                    chooseCityViewModel.addComplexName(complex.residential_complex_name)
                } else {
                    chooseCityViewModel.removeComplexId(complex.id)
                    chooseCityViewModel.removeComplexName(complex.residential_complex_name)
                }
                searchViewModel.updateListFilter("residential_complex", chooseCityViewModel.selectedComplexIds)
            }
        )
    }
    CustomDivider()
}
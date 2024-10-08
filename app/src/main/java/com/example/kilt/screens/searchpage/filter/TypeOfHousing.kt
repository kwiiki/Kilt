package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.R
import com.example.kilt.custom.CustomToggleButton
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun TypeOfHousing(
    modifier: Modifier = Modifier,
    configViewModel: ConfigViewModel,
    searchViewModel: SearchViewModel,
) {
    val dealType by searchViewModel.dealType
    val listingType by searchViewModel.listingType
    val propertyType by searchViewModel.propertyType

    val isResidentialSelected = listingType == 1
    val isCommercialSelected = listingType == 2
    val isRentSelected = dealType == 1
    val isBuySelected = dealType == 2
    val selectedIcon = when (propertyType) {
        1 -> "builds"
        2 -> "house"
        else -> ""
    }
    Column(modifier = modifier) {
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
                text = "Арендовать",
                isSelected = isRentSelected,
                onClick = { searchViewModel.selectRent() },
                modifier = Modifier.weight(1f)
            )
            CustomToggleButton(
                text = "Купить",
                isSelected = isBuySelected,
                onClick = { searchViewModel.selectBuy() },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CustomDivider()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Тип Жилья",
            color = Color(0xff010101),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
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
                text = "Жилая",
                isSelected = isResidentialSelected,
                onClick = { searchViewModel.selectResidential() },
                modifier = Modifier.weight(1f)
            )
            CustomToggleButton(
                text = "Коммерческая",
                isSelected = isCommercialSelected,
                onClick = { searchViewModel.selectCommercial() },
                modifier = Modifier.weight(1f)
            )
        }
        if (isResidentialSelected) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                if (selectedIcon == "builds") Color(0xffe6e6ff) else Color.Transparent,
                                RoundedCornerShape(14.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = Color(0xffF2F2F2),
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { searchViewModel.selectPropertyType(1) }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (selectedIcon == "builds") R.drawable.selected_builds_icon else R.drawable.unselected_builds_icon
                            ),
                            contentDescription = "Icon 1",
                            tint = if (selectedIcon == "builds") Color(0xff3F4FE0) else Color.Unspecified,
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                                .size(35.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Квартира",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                if (selectedIcon == "house") Color(0xffe6e6ff) else Color.Transparent,
                                RoundedCornerShape(14.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = Color(0xffF2F2F2),
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { searchViewModel.selectPropertyType(2) }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (selectedIcon == "house") R.drawable.selected_house_icon else R.drawable.unselected_house_icon
                            ),
                            contentDescription = "Icon 2",
                            tint = if (selectedIcon == "house") Color(0xff3F4FE0) else Color.Unspecified,
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                                .size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Дом",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 27.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
    LaunchedEffect(dealType, listingType, propertyType) {
        configViewModel.setTypes(dealType, listingType, propertyType)
    }
}
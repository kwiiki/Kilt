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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun TypeOfHousing(modifier: Modifier) {
    var isRentSelected by remember { mutableStateOf(true) }
    var isBuySelected by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding()) {
        Text(
            "Тип Жилья",
            color = Color(0xff010101),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(Color(0xffF2F2F2))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomToggleButton(
                text = "Жилая",
                isSelected = isRentSelected,
                onClick = {
                    isRentSelected = true
                    isBuySelected = false
                },
                modifier = Modifier.weight(1f)
            )
            CustomToggleButton(
                text = "Коммерческая",
                isSelected = isBuySelected,
                onClick = {
                    isRentSelected = false
                    isBuySelected = true
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (isRentSelected) {
            var selectedIcon by remember { mutableStateOf("builds") }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
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
                            .clickable { selectedIcon = "builds" }
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
                            .clickable { selectedIcon = "house" }
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
}
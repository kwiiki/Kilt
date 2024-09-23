package com.example.kilt.screens.searchpage.quickFilters

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.R
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun HouseOrFlat(
    searchViewModel: SearchViewModel,
    filterType: String,
    title: String,
    onApplyClick: () -> Unit,
) {
    val propertyType by searchViewModel.propertyType
    val selectedIcon = when (propertyType) {
        1 -> "builds"
        2 -> "house"
        else -> ""
    }
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Spacer(
            modifier = Modifier
                .padding(start = 150.dp)
                .padding(bottom = 8.dp) // Add some padding if needed
                .height(5.dp)
                .width(52.dp)
                .background(Color(0xffDBDFE4), RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Тип недвижимости",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp)) // Ensure space between rows
        Button(
            onClick = {
                searchViewModel.performSearch()
                onApplyClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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

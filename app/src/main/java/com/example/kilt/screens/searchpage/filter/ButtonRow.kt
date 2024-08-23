package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kilt.custom.CustomToggleButton
import com.example.kilt.viewmodels.ConfigViewModel

//@Composable
//fun ButtonRow(modifier: Modifier, configViewModel: ConfigViewModel) {
//    var isRentSelected by remember { mutableStateOf(true) }
//    var isBuySelected by remember { mutableStateOf(false) }
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(50.dp)
//            .clip(shape = RoundedCornerShape(15.dp))
//            .background(Color(0xffF2F2F2))
//            .padding(4.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        CustomToggleButton(
//            text = "Арендовать",
//            isSelected = isRentSelected,
//            onClick = {
//                isRentSelected = true
//                isBuySelected = false
//                configViewModel.setDealType(1)
//            },
//            modifier = Modifier.weight(1f)
//        )
//        CustomToggleButton(
//            text = "Купить",
//            isSelected = isBuySelected,
//            onClick = {
//                isRentSelected = false
//                isBuySelected = true
//                configViewModel.setDealType(2)
//            },
//            modifier = Modifier.weight(1f)
//        )
//    }
//}

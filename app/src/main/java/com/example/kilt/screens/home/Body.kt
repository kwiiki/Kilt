package com.example.kilt.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kilt.R
import com.example.kilt.custom.CustomToggleButton
import com.example.kilt.viewmodels.SearchViewModel


@Composable
fun ButtonRow(searchViewModel: SearchViewModel,modifier: Modifier) {

    val dealType by searchViewModel.dealType

    val isRentSelected = dealType == 1
    val isBuySelected = dealType == 2
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Color(0xffF2F2F2))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomToggleButton1(
            text = "Арендовать",
            isSelected = isRentSelected,
            onClick = {
                searchViewModel.selectRent()
            },
            modifier = Modifier.weight(1f)
        )
        CustomToggleButton1(
            text = "Купить",
            isSelected = isBuySelected,
            onClick = {
                searchViewModel.selectBuy()
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CustomToggleButton1(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundBrush = if (isSelected) Brush.verticalGradient(
        colors = listOf(Color.White, Color.White)
    ) else Brush.verticalGradient(
        colors = listOf(Color(0xFFF2F2F2), Color(0xFFF2F2F2))
    )

    val borderColor = if (isSelected) Color.Blue else Color.Transparent
    val textColor = if (isSelected) Color.Black else Color(0xff566982)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush = backgroundBrush)
            .border(1.2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.W600
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewBottomRow(){
    ButtonRow(searchViewModel = hiltViewModel(), modifier = Modifier)
}

//@Composable
//@Preview(showBackground = true)
//fun PreviewBody() {
//    Body(Modifier.padding(horizontal = 16.dp))
//}
package com.example.kilt.screens.profile.myannouncement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChooseListingType(
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
) {
    val options = listOf(
        "Активные" to "default",
        "Модерация" to "new",
        "Архив" to "price_asc",
    )
    Column(
        modifier = Modifier
            .height(180.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        options.forEach { (optionText, sortValue) ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        onOptionSelected(optionText)  // This now updates the selectedType
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = optionText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
                if (selectedOption == optionText) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "CheckIcon"
                    )
                }
            }
        }
    }
}
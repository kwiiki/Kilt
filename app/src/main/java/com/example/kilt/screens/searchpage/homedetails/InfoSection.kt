package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun InfoSection() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (column1, column2) = createRefs()

        Column(
            modifier = Modifier.constrainAs(column1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            DetailItemRight(label = "Тип недвижимости")
            DetailItemRight(label = "Количество комнат")
            DetailItemRight(label = "Этаж")
            DetailItemRight(label = "Мебилирована")
            DetailItemRight(label = "Площадь")
        }

        Column(
            modifier = Modifier.constrainAs(column2) {
                start.linkTo(column1.end, margin = 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            DetailItemLeft(value = "Квартира")
            DetailItemLeft(value = "2")
            DetailItemLeft(value = "2 из 6")
            DetailItemLeft(value = "Полностью мебелирован")
            DetailItemLeft(value = "44 м²")
        }
    }
}

@Composable
fun DetailItemRight(label: String? = null, value: String? = null) {
    Text(
        text = label ?: value.orEmpty(),
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun DetailItemLeft(label: String? = null, value: String? = null) {
    Text(
        text = label ?: value.orEmpty(),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
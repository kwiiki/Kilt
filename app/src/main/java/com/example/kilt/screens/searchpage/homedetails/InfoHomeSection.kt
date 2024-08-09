package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun InfoHomeSection() {
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
            DetailItemRight(label = "Год постройки")
            DetailItemRight(label = "Тип строения")

        }

        Column(
            modifier = Modifier.constrainAs(column2) {
                start.linkTo(column1.end, margin = 54.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            DetailItemLeft(value = "1981")
            DetailItemLeft(value = "Кирпич")

        }
    }
}
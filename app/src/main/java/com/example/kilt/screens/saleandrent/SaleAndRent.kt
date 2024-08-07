package com.example.kilt.screens.saleandrent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SaleAndRent(){
    Column(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        SearchAndFilterSection()

    }


}

@Composable
@Preview(showBackground = true)
fun PreviewSaleAndRent(){
    SaleAndRent()
}
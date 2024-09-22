package com.example.kilt.screens.searchpage.quickFilters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel


@Composable
fun ListQuickFilter(
    configViewModel: ConfigViewModel,
    searchViewModel: SearchViewModel,
    prop: String,
    title: String
) {
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
            text = "Количество комнат",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        ListFilterForQuick(
            viewModel = configViewModel,
            prop = prop,
            title = title,
            searchViewModel = searchViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                searchViewModel.performSearch()
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
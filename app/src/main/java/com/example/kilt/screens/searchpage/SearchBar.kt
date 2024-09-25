package com.example.kilt.screens.searchpage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.padding()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(45.dp)
                .clickable { /* Handle click action */ }
                .background(Color.Transparent, shape = RoundedCornerShape(14.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFFDBDFE4),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Город, район, ЖК",
                color = Color(0xff566982),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearchBar() {
    SearchBar()

}
package com.example.kilt.screens.searchpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kilt.R


@Composable
fun SearchAndFilterSection() {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchBar()
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.vec),
                contentDescription = "Значок электронной почты",
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xffF2F2F2), shape =(RoundedCornerShape(16.dp)))

            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(alignment = Alignment.Center)
                )
            }

            FilterScreen()
        }

    }


}

@Composable
@Preview(showBackground = true)
fun PreviewSearchAndFilterSection() {
    SearchAndFilterSection()
}
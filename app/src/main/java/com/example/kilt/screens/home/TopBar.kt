package com.example.kilt.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kilt.R


@Composable
fun TopBar() {
    val img = painterResource(id = R.drawable.frame)
    val logo = painterResource(id = R.drawable.logo)

    Column(modifier = Modifier.fillMaxWidth(),) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp)
        ) {
            Image(
                painter = img,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = logo,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(96.dp)
                    .height(65.dp)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewTopBar() {
    TopBar()
}
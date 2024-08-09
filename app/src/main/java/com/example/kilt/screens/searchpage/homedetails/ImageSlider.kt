package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kilt.R

@Composable
fun ImageSlider(modifier: Modifier) {
    val scrollState = rememberScrollState()
    var fullscreenImage by remember { mutableStateOf<Int?>(null) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Image(
            painter = painterResource(id = R.drawable.kv1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { fullscreenImage = R.drawable.kv1 },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(id = R.drawable.im1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { fullscreenImage = R.drawable.im1 },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(id = R.drawable.blogphoto2),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(id = R.drawable.im2),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(id = R.drawable.im3),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
    fullscreenImage?.let { imageRes ->
        FullscreenImage(
            imageRes = imageRes,
            onDismiss = { fullscreenImage = null }
        )
    }
}

@Composable
fun FullscreenImage(imageRes: Int, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(onClick = onDismiss)
//            .padding(vertical = 130.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
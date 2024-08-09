package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { fullscreenImage = R.drawable.im1 }
        ) {
            Image(
                painter = painterResource(id = R.drawable.im1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
            ) {
                IconButton(onClick = { /* Handle back action */ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_icon),
                        contentDescription = "Back",
                        tint = Color(0xff566982),
                        modifier = Modifier.size(70.dp)
                    )
                }
                Spacer(modifier = Modifier.width(270.dp))
                IconButton(onClick = { /* Handle favorite action */ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.favorite_icon),
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier.size(70.dp).background(Color(0xff566982))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Repeat for other images without overlay
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
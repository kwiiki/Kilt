package com.example.kilt.screens.searchpage.homedetails

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.data.config.Image
import com.example.kilt.viewmodels.HomeSaleViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ImageSlider(
    modifier: Modifier,
    onFullScreenToggle: (Boolean) -> Unit,
    navController: NavHostController
) {
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    val homeSale by homeSaleViewModel.homeSale
    homeSaleViewModel.loadHomesale()
    val listing = homeSale?.listing
    val imageList = listing?.images ?: emptyList()
    Log.d("wwwww", "ImageSlider: ${imageList.toString()}")

    var selectedImage by remember { mutableStateOf<Image?>(null) }
    val listState = rememberLazyListState()

    if (selectedImage != null) {
        FullScreenPhotoScreen(
            photoUrl = "https://kilt-cdn.kz/${selectedImage!!.link}",
            onClose = {
                selectedImage = null
                onFullScreenToggle(false)
            },
        )
        onFullScreenToggle(true)
    } else {
        PhotosScreen(
            images = imageList,
            onPhotoClick = { selectedImage = it },
            onBackClick = { /* Handle back click */ },
            onFavoriteClick = { /* Handle favorite click */ },
            listState = listState
        )
    }
}


@Composable
fun PhotosScreen(
    images: List<Image>,
    onPhotoClick: (Image) -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    listState: LazyListState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (images.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
            }
        } else {
            LazyColumn(state = listState) {
                items(images) { image ->
                    PhotoItem(image, onPhotoClick)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_icon),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0xff19334d))
                        .size(70.dp)
                )

            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.favorite_icon),
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0xff19334d))
                        .size(70.dp)
                )
            }
        }
    }
}

@Composable
fun PhotoItem(image: Image, onPhotoClick: (Image) -> Unit) {
    val photoUrl = "https://kilt-cdn.kz/${image.link}"

    val scaleFactor = 620f / image.width.toFloat()
    val adjustedHeight = image.height.toFloat() * scaleFactor

    Box(
        modifier = Modifier
            .width(620.dp)
            .padding(vertical = 4.dp)
            .height(adjustedHeight.dp)
            .clickable { onPhotoClick(image) }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FullScreenPhotoScreen(photoUrl: String, onClose: () -> Unit) {
    var offsetY by remember { mutableStateOf(0f) }
    val dismissThreshold = 300f
    var isClosed by remember { mutableStateOf(false) }
    val offsetYAnimated by animateFloatAsState(
        targetValue = offsetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val alphaAnimated by animateFloatAsState(
        targetValue = if (offsetY.absoluteValue > dismissThreshold) 0f else 1f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    LaunchedEffect(isClosed) {
        if (isClosed) {
            delay(200) // завершения анимации
            onClose()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        offsetY += dragAmount
                    },
                    onDragEnd = {
                        if (offsetY.absoluteValue > dismissThreshold) {
                            isClosed = true
                        } else {
                            offsetY = 0f
                        }
                    }
                )
            }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, offsetYAnimated.roundToInt()) }
                .alpha(alphaAnimated)
                .animateContentSize()
        )
    }
}
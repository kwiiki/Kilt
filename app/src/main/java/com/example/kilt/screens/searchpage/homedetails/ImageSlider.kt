@file:OptIn(ExperimentalPagerApi::class)

package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kilt.R
import com.example.kilt.data.config.Image
import com.example.kilt.navigation.NavPath
import com.example.kilt.utills.imageCdnUrl
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ImageSlider(
    imageList: List<Image>?,
    modifier: Modifier = Modifier,
    onFullScreenToggle: (Boolean) -> Unit,
    navController: NavHostController,
) {
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    var isLoading by remember { mutableStateOf(true) }
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        homeSaleViewModel.loadHomeSale()
        isLoading = false
    }
    val listState = rememberLazyListState()

    when {
        selectedImageIndex != null -> {
            FullScreenPhotoScreen(
                images = imageList ?: emptyList(),
                selectedIndex = selectedImageIndex ?: 0,
                onClose = {
                    selectedImageIndex = null
                    onFullScreenToggle(false)
                },
            )
            onFullScreenToggle(true)
        }
        isLoading || imageList == null -> {
            LoadingIndicator()
        }
        else -> {
            PhotosScreen(
                images = imageList,
                onPhotoClick = { image ->
                    selectedImageIndex = imageList.indexOf(image)
                },
                onBackClick = { navController.navigate(NavPath.SEARCH.name) },
                onFavoriteClick = { /* Handle favorite click */ },
                listState = listState
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Gray,
            modifier = Modifier.size(50.dp)
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
                    color = Color.Gray,
                    modifier = Modifier.size(50.dp)
                )
            }
            if (images.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_empty),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.camera_icon),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
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
    val photoUrl = "${imageCdnUrl}${image.link}"
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value

    val scaleFactor = screenWidth / image.width.toFloat()
    val adjustedHeight = image.height.toFloat() * scaleFactor

    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .height(adjustedHeight.dp)
            .clickable { onPhotoClick(image) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .listener(
                    onSuccess = { _, _ -> isLoading = false },
                    onError = { _, _ -> isLoading = false }
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(adjustedHeight.dp)
                .width(500.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun FullScreenPhotoScreen(
    images: List<Image>,
    selectedIndex: Int,
    onClose: () -> Unit
) {
    var offsetY by remember { mutableStateOf(0f) }
    val dismissThreshold = 100f
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
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = ""
    )
    val pagerState = rememberPagerState(initialPage = selectedIndex)

    LaunchedEffect(isClosed) {
        if (isClosed) {
            delay(100)
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
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, offsetYAnimated.roundToInt()) }
                .alpha(alphaAnimated)
                .animateContentSize()
        ) { page ->
            val image = images[page]
            val photoUrl = "${imageCdnUrl}${image.link}"
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = "${pagerState.currentPage + 1} / ${images.size}",
            color = Color.White,
            fontSize = 18.sp,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .background(Color(0x80000000), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 8.dp)
                .background(Color(0x80000000), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

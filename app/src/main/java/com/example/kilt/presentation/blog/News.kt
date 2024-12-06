package com.example.kilt.presentation.blog

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.screens.home.LazyRowForBlog


@Composable
fun News(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Log.d("NewsScreen", "News: NewsPage")
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                Log.d("NewsScreen", "Back button clicked")
                navController.navigateUp()
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .zIndex(1f) // Ensure it's at the highest z-order
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_icon),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .background(Color(0xff19334d))
                    .size(40.dp) // Adjust size if needed
            )
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            val img1 = painterResource(id = R.drawable.blogphoto2)
            val img2 = painterResource(id = R.drawable.blogphoto1)

            Image(
                painter = img1,
                contentDescription = "blogphoto1",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "7 июля, 2022 года", style = MaterialTheme.typography.labelSmall)

                Text(
                    text = "Когда в присоединённых районах Алматы появится качественная вода",
                    fontSize = 26.sp,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelMedium,
                    lineHeight = 25.sp
                )

                Text(
                    text = "Проблема качественного водоснабжения в присоединённых районах Алматы постепенно решается...",
                    lineHeight = 24.sp, style = MaterialTheme.typography.bodyLarge
                )

                Image(
                    painter = img2,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Запуск объектов обеспечит центральным водоснабжением более 150 тысяч жителей...",
                    lineHeight = 24.sp,
                    style = MaterialTheme.typography.bodyLarge
                )

                LazyRowForBlog(modifier = Modifier, navController = navController)
            }
        }
    }
}


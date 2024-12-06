package com.example.kilt.presentation.blog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.models.Blog
import com.example.kilt.core.navigation.NavPath


val blogs: List<Blog> = listOf(
    Blog(
        0,
        "Когда в присоединённых районах Алматы появится качественная вода",
        img = R.drawable.rectangle2
    ),
    Blog(
        1,
        "Проблема качественного водоснабжения в присоединённых районах Алматы постепенно решается",
        img = R.drawable.blog2
    ),
    Blog(2, "Инструкция покупки квартиры", img = R.drawable.rectangle2),
)

@Composable
fun BlogPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow to Back",
                    tint = Color(0xff566982),
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.38f))
            Text(
                text = "Блог",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
        ) {
            items(blogs) { blog ->
                BlogItems(blog, navController)
            }
        }

    }
}

@Composable
fun BlogItems(blog: Blog, navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { navController.navigate(NavPath.NEWS.name) }) {
        Image(
            painter = painterResource(id = blog.img), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "История",
            fontSize = 14.sp,
            color = Color(0xff3244E4),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = blog.title, fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBlogItem() {
}


@Composable
@Preview(showBackground = true)
fun PreviewBlogPage() {
    val navController = rememberNavController()
    BlogPage(navController)
}
package com.example.kilt.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.data.Blog


val blogs: List<Blog> = listOf(
    Blog(0, "Инструкция покупки квартиры", img = R.drawable.rectangle2),
    Blog(1, "Инструкция покупки квартиры", img = R.drawable.rectangle2),
    Blog(2, "Инструкция покупки квартиры", img = R.drawable.rectangle2),
    Blog(2, "Инструкция покупки квартиры", img = R.drawable.rectangle2),
)

@Composable
fun LazyRowForBlog(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier.height(290.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Наш блог", fontSize = 25.sp, style = MaterialTheme.typography.labelMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate("BlogPage")
                }
            ) {
                Text(
                    text = "Смотреть все",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0XFF3244E4)

                )
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "content description",
                    tint = Color(0XFF3244E4)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .height(400.dp)
        ) {
            items(blogs) { blog ->
                BlogItem(blog,navController)
            }
        }


    }

}

@Composable
fun BlogItem(blog: Blog, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp)
            .height(330.dp)
            .clickable {
                navController.navigate("News")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(8.dp)
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = blog.img),
                contentDescription = blog.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = blog.title,
                    color = Color.Black,
                    fontWeight = FontWeight.W600,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { /* TODO */ },
                    border = BorderStroke(
                        width = 2.dp, color = Color(0xffEAEAEC)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier
                        .height(48.dp)
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Покупка квартиры",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xff6B6D79)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLazyRowForBlog() {


}
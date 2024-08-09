package com.example.kilt.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R

@Composable
fun FavoritesScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Избранные",
            fontWeight = FontWeight.W800,
            fontSize = 36.sp,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.favorite_star_img), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = "Добавляйте объявления в избранное",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Отмечайте интересные объявления и следите за изменением цены",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xff566982)),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            SearchAnnouncementButton(navController)
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewFavoriteScreen() {
    val navController = rememberNavController()
    FavoritesScreen(navController)

}
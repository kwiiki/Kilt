package com.example.kilt.screens.searchpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.data.Home

val gradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
)
@Composable
fun PropertyItem(home: Home, navController: NavHostController) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(Color(0xffFFFFFF))
            .clickable { navController.navigate("HomeDetails") },
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column(modifier = Modifier.background(Color(0xffFFFFFF))) {
            Box {
                Image(
                    painter = painterResource(id = home.homeImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                IconButton(
                    onClick = { /* TODO: Handle favorite click */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color(0xff6B6D79),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Chip(text = "Собственник")
//                Chip(text = "Хорошая цена")
//                Chip(text = "С отделкой")
            }

            Text(
                text = "${home.price} ₸",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
            )

            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                IconText(
                    icon = ImageVector.vectorResource(id = R.drawable.group_icon),
                    text = "${home.roomCount} комн"
                )
                Spacer(modifier = Modifier.width(12.dp))
                IconText(
                    icon = ImageVector.vectorResource(id = R.drawable.room_icon),
                    text = "${home.homeArea} м²"
                )
                Spacer(modifier = Modifier.width(10.dp))
                IconText(
                    icon = ImageVector.vectorResource(id = R.drawable.building_icon),
                    text = "${home.roomCount}/${home.homeMaxFloor}"
                )
            }

            Text(
                text = home.address,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xff6B6D79),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GradientButton(
                    text = "Позвонить",
                    textColor = Color.White,
                    gradient = gradient,
                    onClick = { /* TODO: Handle click */ },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                WhatsAppGradientButton(
                    text = "Написать",
                    onClick = { /* TODO: Handle click */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Card(
        onClick = { /*TODO*/ },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),

        ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.chield_check_fill),
                contentDescription = null,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, color = Color(0xFF56B375))
        }
    }

}

@Composable
fun IconText(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 14.sp, color = Color(0xff6B6D79))
    }
}
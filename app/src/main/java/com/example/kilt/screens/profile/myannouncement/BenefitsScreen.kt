package com.example.kilt.screens.profile.myannouncement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.R

@Composable
fun BenefitsScreen() {
    val listColor = listOf(Color(0xFF1B278F), Color(0xFF3244E4))
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color(0xFF566982),
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable {
                    }
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.22f))
            Text(
                text = "Продвижение в поиске",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xff01060E)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color(0xFFEFF1F4))
                .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Баланс:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF01060E)
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
                Text(
                    text = "5000",
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = listColor,
                            tileMode = TileMode.Mirror
                        ),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W700
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.kilt_money),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        PlatinumTariff()
        GoldTariff()
        SilverTariff()
        JustButtonTariff()
    }
}
@Composable
fun JustButtonTariff(){
    val colors = listOf(Color(0xFFF90870), Color(0xFF3244E4))
    val gradientBrush = Brush.horizontalGradient(
        colors = colors,
        startX = 0f,
        endX = 1200f
    )
    val colorsForBorder = listOf(Color(0xFF3244E4), Color(0xFFF90870))
    val gradientForBorder = Brush.horizontalGradient(
        colors = colorsForBorder,
        startX = 0f,
        endX = 1200f
    )
    Box{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(gradientBrush, shape = RoundedCornerShape(16.dp))
            .border(width = 3.dp, brush = gradientForBorder, shape = RoundedCornerShape(12.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Button",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Обратите больше внимание на объявление с помощью яркой кнопки",
                    color = Color.White,
                    lineHeight = 18.sp,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 15.dp, end = 35.dp),
                    letterSpacing = 0.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "450 Т/день",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
        Image(
            painter = painterResource(id = R.drawable.button_image_for_tarif),
            contentDescription = null,
            modifier = Modifier
                .size(146.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (25).dp, y = (-10).dp)
        )
    }
}
@Composable
fun SilverTariff(){
    val colors = listOf(Color(0xFF1B278F), Color(0xFF3244E4))
    val gradientBrush = Brush.horizontalGradient(
        colors = colors,
        startX = 0f,
        endX = 1200f
    )
    val colorsForBorder = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
    val gradientForBorder = Brush.horizontalGradient(
        colors = colorsForBorder,
        startX = 0f,
        endX = 1200f
    )
    Box{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(gradientBrush, shape = RoundedCornerShape(16.dp))
            .border(width = 3.dp, brush = gradientForBorder, shape = RoundedCornerShape(12.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Silver",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• В начало списка на 24 часа под объявления “Gold”\n" +
                            "• Парящая карточка “Silver”\n" +
                            "• Продления публикации объявления до 10 дей ",
                    color = Color.White,
                    lineHeight = 18.sp,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 15.dp, end = 35.dp),
                    letterSpacing = 0.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "450 Т/день",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
        Image(
            painter = painterResource(id = R.drawable.up_arrow),
            contentDescription = null,
            modifier = Modifier
                .size(190.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (15).dp, y = (0).dp)
        )
    }
}

@Composable
fun PlatinumTariff(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.on), contentDescription = null)
        }

    }

}

@Composable
fun GoldTariff(){
    val colors = listOf(Color(0xFFAA895F), Color(0xFFE0D8CC))
    val gradientBrush = Brush.horizontalGradient(
        colors = colors,
        startX = 0f,
        endX = 1200f
    )
    val colorsForBorder = listOf(Color(0xFFE0D8CC), Color(0xFFAA895F))
    val gradientForBorder = Brush.horizontalGradient(
        colors = colorsForBorder,
        startX = 0f,
        endX = 1200f
    )
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(gradientBrush, shape = RoundedCornerShape(16.dp))
                .border(width = 3.dp, brush = gradientForBorder, shape = RoundedCornerShape(12.dp))

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                        .padding(end = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Gold",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• Топ 3 в начале списка на 24 часа\n" +
                                "• Парающая карточка “Gold”\n" +
                                "• Не меняет позицию при сортировке\n" +
                                "• Продления публикации объявления до 10 дней",
                        color = Color.White,
                        lineHeight = 18.sp,
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 15.dp, end = 35.dp),
                        letterSpacing = 0.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "450 Т/день",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.gold_home),
            contentDescription = null,
            modifier = Modifier
                .size(151.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (10).dp, y = (-5).dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewTariffScreen() {
    BenefitsScreen()
}
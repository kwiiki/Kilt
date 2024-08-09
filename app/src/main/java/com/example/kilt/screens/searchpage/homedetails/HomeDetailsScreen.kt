package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.kilt.R
import com.example.kilt.data.Home
import com.example.kilt.screens.searchpage.IconText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDetailsScreen() {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val home: Home = Home(0, 555555, 3, 54, 3, 10, "Абая 33", homeImg = R.drawable.kv1)

    Box(modifier = Modifier.fillMaxSize().padding(bottom = 0.dp)) {
        BottomSheetScaffold(
            sheetContainerColor = Color(0xffffffff),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 215.dp,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(630.dp)
                        .padding(horizontal = 8.dp)
                        .background(Color(0xffffffff))
                        .verticalScroll(scrollState),
                ) {
                    Text(
                        text = "${home.price} ₸",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
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
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color(0xffDBDFE4))
                    )
                    Calculator()

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color(0xffDBDFE4))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val mapImage = painterResource(id = R.drawable.map_image)
                    Image(
                        painter = mapImage,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp), contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Описание",
                        color = Color.Black,
                        fontWeight = FontWeight.W700,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    ApartmentDescription()
                    Text(
                        text = "Информация",
                        color = Color.Black,
                        fontWeight = FontWeight.W700,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    InfoSection()

                    Text(
                        text = "О Доме",
                        color = Color.Black,
                        fontWeight = FontWeight.W700,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )

                    InfoHomeSection()

                    Spacer(modifier = Modifier.height(110.dp))
                }
            }
        ) { innerPadding ->
            // ImageSlider
           ImageSlider(Modifier.padding(innerPadding))


        }
        BottomDetails(modifier = Modifier.zIndex(1f))

    }
}

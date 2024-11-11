@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.kilt.screens.searchpage.GradientButton
import com.example.kilt.screens.searchpage.IconText
import com.example.kilt.screens.searchpage.homedetails.formatNumber
import com.example.kilt.screens.searchpage.homedetails.gradient

@Composable
fun MyAnnouncementScreen(navController: NavHostController) {
    val announcementList =
        listOf(ListingItem(), ListingItem(id = 13), ListingItem(id = 32), ListingItem())
    var chooseListingType by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    var selectedType by remember { mutableStateOf("Активные") }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var selectItem by remember { mutableStateOf(false) }
    var selectedItemSettings by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(Color.White)
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
                        navController.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.26f))
            Text(
                text = "Мои объявления",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xff01060E)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable { chooseListingType = true }
                    .background(Color.Transparent, shape = RoundedCornerShape(14.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFDBDFE4),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = selectedType,
                    color = Color(0xff010101),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Search",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(25.dp)
                )
            }
        }
        if (chooseListingType) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                sheetState = bottomSheetState,
                onDismissRequest = { chooseListingType = false },
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomSheetDefaults.DragHandle(color = Color(0xff010101))
                    }
                }
            ) {
                ChooseListingType(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        selectedType = option
                        chooseListingType = false
                    },
                )
            }
        }
        if(selectItem){
            ModalBottomSheet(
                tonalElevation = 20.dp,
                sheetState = bottomSheetState,
                onDismissRequest = { selectItem = false },
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomSheetDefaults.DragHandle(color = Color(0xff010101))
                    }
                }
            ) {
                ItemSettings(

                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(announcementList) { item ->
                AnnouncementItem(
                    announcementItem = item,
                    onMoreClick = { selectItem = true },
                    Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ItemSettings(){
    val options = listOf(
        "Продвигать" to "default",
        "Редактировать объявление" to "new",
        "Отправить в архив" to "price_asc",
        "Договор аренды" to "price_asc",
    )
    Column(
        modifier = Modifier
            .height(220.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        options.forEach { (optionText, sortValue) ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
//                        onOptionSelected(optionText)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = optionText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
//                if (selectedOption == optionText) {
//                    Icon(
//                        Icons.Default.Check,
//                        contentDescription = "CheckIcon"
//                    )
//                }
            }
        }
    }
}


@Composable
fun AnnouncementItem(
    announcementItem: ListingItem,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .background(Color(0xffFFFFFF))
            .clickable {
            },
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column(modifier = Modifier.background(Color(0xffFFFFFF))) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.im1),
                    contentDescription = null,
                    modifier = Modifier
                        .height(160.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { onMoreClick() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        modifier = Modifier.background(color = Color.White),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ChipForAnnouncement(text = "Хорошая цена")
            }
            Text(
                text = "${formatNumber(announcementItem.price)} ₸",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
            )
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                IconText(
                    icon = ImageVector.vectorResource(id = R.drawable.group_icon),
                    text = "${announcementItem.numRoom} комн"
                )
                Spacer(modifier = Modifier.width(12.dp))
                val area = announcementItem.area
                val formattedArea = if (area.rem(1) == 0.0) {
                    area.toInt().toString()
                } else {
                    area.toString()
                }
                IconText(
                    icon = ImageVector.vectorResource(id = R.drawable.room_icon),
                    text = "$formattedArea м²"
                )
                Spacer(modifier = Modifier.width(10.dp))
                if (announcementItem.floor != 0 && announcementItem.numFloor != null) {
                    IconText(
                        icon = ImageVector.vectorResource(id = R.drawable.building_icon),
                        text = "${announcementItem.floor}/${announcementItem.numFloor}"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Text(
                text = announcementItem.address,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 14.sp,
                color = Color(0xff6B6D79),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GradientButton(
                    text = "Посмотреть",
                    textColor = Color.White,
                    gradient = gradient,
                    onClick = { /* TODO: Handle click */ },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ChipForAnnouncement(text: String) {
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
            Text(text = text, color = Color(0xFF3244E4))
        }
    }
}

data class ListingItem(
    val id: Int = 112,
    val price: String = "12123",
    val numRoom: Int = 3,
    val area: Double = 13.4,
    val floor: Int = 3,
    val numFloor: Int = 5,
    val address: String = "Алматы, пр. Абая 150/230"
)
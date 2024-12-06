package com.example.kilt.presentation.userabout

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.domain.userabout.model.Listing
import com.example.kilt.models.authentification.User
import com.example.kilt.utills.enums.UserType
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.presentation.userabout.viewmodel.UserAboutViewModel
import com.example.kilt.presentation.identification.DocumentRow
import com.example.kilt.screens.profile.myannouncement.ChipForAnnouncement
import com.example.kilt.screens.searchpage.GradientButton
import com.example.kilt.screens.searchpage.IconText
import com.example.kilt.screens.searchpage.filter.CustomDivider
import com.example.kilt.screens.searchpage.filter.formatNumber
import com.example.kilt.presentation.listing.gradient
import com.example.kilt.utills.imageKiltUrl
import com.example.kilt.presentation.login.viewModel.AuthViewModel

@Composable
fun UserAboutScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userAboutViewModel: UserAboutViewModel
) {
    val userWithMetadata by authViewModel.user.collectAsState(initial = null)
    val user = userWithMetadata?.user
    val agencyAboutList = listOf("Личные данные", "Место работы", "О себе")
    val expandedItems = remember { mutableStateMapOf<String, Boolean>() }
    val userListings by userAboutViewModel.userListings
    val salesCount = userAboutViewModel.countOfDealType1
    val rentCount = userAboutViewModel.countOfDealType2

    LaunchedEffect(Unit) {
        userAboutViewModel.fetchUserListings()
            userAboutViewModel.updateFilter(
                dealType = userAboutViewModel.selectedDealType,
                propertyType = userAboutViewModel.selectedPropertyType
            )
        Log.d("listings", "UserAboutScreen: ${userListings.listing}")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color(0xFF566982),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.3f))
                Text(
                    text = "Профиль",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xff01060E)
                )
            }
        }
        item {
            val userTypeText = when (user?.user_type) {
                "owner" -> UserType.OWNER.ruText
                "specialist" -> UserType.AGENT.ruText
                "agency" -> UserType.AGENCY.ruText
                else -> ""
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                    .clickable { navController.navigate(NavPath.EDITPROFILE.name) },
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (user?.photo != "") {
                        AsyncImage(
                            model = "$imageKiltUrl${user?.photo}",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .background(color = Color.Transparent, RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.non_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(56.dp)
                                .background(color = Color.Transparent, RoundedCornerShape(12.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Row {
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.chield_check_fill),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = userTypeText,
                                color = Color(0xFF2AA65C),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W700,
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = user?.firstname ?: "Не Указано",
                            fontWeight = FontWeight.W700,
                            fontSize = 20.sp,
                            color = Color(0xff010101)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(agencyAboutList) { item ->
            val isExpanded = expandedItems[item] ?: false
            DocumentRow(
                title = item,
                expanded = isExpanded,
                onClick = { expandedItems[item] = !isExpanded }
            )
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                when (item) {
                    "Личные данные" -> UserPersonalData(user)
                    "Место работы" -> WorkPlaceData(user)
                    "О себе" -> AboutData(user)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            CustomDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Предложения",
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                color = Color(0xFF01060E)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilterButton(
                        text = "Аренда (${salesCount.value})",
                        isSelected = userAboutViewModel.selectedDealType == 1,
                        onClick = {
                            userAboutViewModel.updateFilter(
                                dealType = 1,
                                propertyType = userAboutViewModel.selectedPropertyType
                            )
                        }
                    )
                    FilterButton(
                        text = "Продажа (${rentCount.value})",
                        isSelected = userAboutViewModel.selectedDealType == 2,
                        onClick = {
                            userAboutViewModel.updateFilter(
                                dealType = 2,
                                propertyType = userAboutViewModel.selectedPropertyType
                            )
                        }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilterButton(
                        text = "Квартира",
                        isSelected = userAboutViewModel.selectedPropertyType == 1,
                        onClick = {
                            Log.d("flatButton", "UserAboutScreen: flat button clicked")
                            userAboutViewModel.updateFilter(
                                dealType = userAboutViewModel.selectedDealType,
                                propertyType = 1
                            )
                        }
                    )
                    FilterButton(
                        text = "Дом",
                        isSelected = userAboutViewModel.selectedPropertyType == 2,
                        onClick = {
                            userAboutViewModel.updateFilter(
                                dealType = userAboutViewModel.selectedDealType,
                                propertyType = 2
                            )
                        }
                    )
                    FilterButton(
                        text = "Коммерция",
                        isSelected = userAboutViewModel.selectedPropertyType == 6,
                        onClick = {
                            userAboutViewModel.updateFilter(
                                dealType = userAboutViewModel.selectedDealType,
                                propertyType = 6
                            )
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(userAboutViewModel.filteredListings.value.listing) { listing ->
            MyAnnouncement(navController, listing)
        }
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(42.dp)
            .border(
                width = 1.5.dp,
                color = if (isSelected) Color.Blue else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isSelected) Color(0xFFEFF1F4) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isSelected) Color.Blue else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MyAnnouncement(navController: NavHostController, announcementItem: Listing) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { },
        elevation = CardDefaults.cardElevation(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFFFF)),
    ) {
        Column(modifier = Modifier.background(Color(0xffFFFFFF))) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Log.d("announcementImage", "MyAnnouncement: ${announcementItem.image}")
                if (announcementItem.image == " ") {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.image_empty),
                            contentDescription = null,
                            modifier = Modifier.height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.camera_icon),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                } else {
                    AsyncImage(
                        model = "$imageKiltUrl${announcementItem.image}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(160.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color(0xff6B6D79),
                        )
                    }
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
                    text = "${announcementItem.numRooms} комн"
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
                if (announcementItem.numFloor != 0) {
                    IconText(
                        icon = ImageVector.vectorResource(id = R.drawable.building_icon),
                        text = "${announcementItem.numFloor}/${announcementItem.numFloors}"
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
                    text = "Посмотреть объявление",
                    textColor = Color.White,
                    gradient = gradient,
                    onClick = { navController.navigate("${NavPath.HOMEDETAILS.name}/${announcementItem.id}") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun UserPersonalData(user: User?) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        DetailItemForAgency(label = "Телефон", value = formatPhoneNumber(user?.phone))
        DetailItemForAgency(label = "ID", value = formatPhoneNumber(user?.id.toString()))
    }
}

@Composable
fun WorkPlaceData(user: User?) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DetailItemForAgency(label = "Город", value = user!!.agent_city)
        DetailItemForAgency(label = "Полный адрес", value = user.agent_full_address)
        DetailItemForAgency(label = "Сотрудники", value = "0")
        DetailItemForAgency(label = "График работы", value = user.agent_working_hours)
    }
}

@Composable
fun AboutData(user: User?) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        DetailItemForAgency(label = "О себе", value = user!!.agent_about)
    }
}

fun formatPhoneNumber(phone: String?): String {
    val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
    return phone?.let { regex.replace(it, "$1 $2 $3 $4 $5") } ?: ""
}

@Composable
fun DetailItemForAgency(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Visible
        )
    }
}

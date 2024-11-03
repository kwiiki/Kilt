@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.kilt.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.models.authentification.User
import com.example.kilt.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.screens.searchpage.homedetails.DetailItem
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun AgencyProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val userWithMetadata by authViewModel.user.collectAsState(initial = null)
    var openFilterBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val user = userWithMetadata?.user
    val agencyAboutList = listOf("Личные данные", "Место работы", "О себе")
    val expandedItems = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
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
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.26f))
            Text(
                text = "Профиль агентства",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xff01060E)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            IconButton(onClick = { openFilterBottomSheet = true }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        if (openFilterBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                sheetState = bottomSheetState,
                onDismissRequest = { openFilterBottomSheet = false },
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
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.White)) {
                    TextButton(onClick = {
                        navController.navigate(NavPath.EDITPROFILE.name)
                        openFilterBottomSheet = false
                    }) {
                        Text(
                            text = "Редактировать профиль",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(1.dp).background(color = Color.Gray))
                    }
                }

            }
        }
        val userTypeText = when (user?.user_type) {
            "owner" -> UserType.OWNER.ruText
            "specialist" -> UserType.AGENT.ruText
            "agency" -> UserType.AGENCY.ruText
            else -> ""
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
                .height(113.dp)
                .padding(16.dp)
                .clickable { navController.navigate(NavPath.AGENCYPROFILESCREEN.name) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.non_image),
                contentDescription = null,
                modifier = Modifier
                    .size(83.dp)
                    .background(color = Color.Transparent, RoundedCornerShape(12.dp))
            )
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
                        fontWeight = FontWeight.W700
                    )
                }
                Text(
                    text = user?.firstname ?: "Не Указано",
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    color = Color(0xff010101)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatPhoneNumber(user?.phone),
                    color = Color(0xff010101),
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dp))

        agencyAboutList.forEach { item ->
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
    }
}

@Composable
fun UserPersonalData(user: User?) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        DetailItem(label = "Телефон", value = formatPhoneNumber(user?.phone))
    }
}

@Composable
fun WorkPlaceData(user: User?) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DetailItem(label = "Город", value = user!!.agent_city)
        DetailItem(label = "Полный адрес", value = user.agent_full_address)
        DetailItem(label = "Сотрудники", value = "0")
        DetailItem(label = "График работы", value = user.agent_working_hours)
    }
}

@Composable
fun AboutData(user: User?) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        DetailItem(label = "О себе", value = user!!.agent_about)
    }
}

fun formatPhoneNumber(phone: String?): String {
    val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
    return phone?.let { regex.replace(it, "$1 $2 $3 $4 $5") } ?: ""
}

@Composable
@Preview(showBackground = true)
fun PreviewAgencyProfile() {
    AgencyProfileScreen(navController = rememberNavController(), authViewModel = hiltViewModel())
}
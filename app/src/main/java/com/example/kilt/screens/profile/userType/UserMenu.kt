@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.profile.userType

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.navigation.NavPath
import com.example.kilt.presentation.editprofile.addnewimagebottomsheet.AddNewImage
import com.example.kilt.presentation.editprofile.components.CustomButtonForEdit
import com.example.kilt.presentation.editprofile.components.SaveButton
import com.example.kilt.presentation.editprofile.gradientBrush
import com.example.kilt.presentation.editprofile.listColor
import com.example.kilt.screens.profile.login.PhoneNumberTextField
import com.example.kilt.ui.theme.DefaultBlack
import com.example.kilt.ui.theme.DefaultGray
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun UserMenu(navController: NavHostController, authViewModel: AuthViewModel) {
    var openBottomSheet by remember { mutableStateOf(false) }
    val bottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.plus_icon),
            title = "Добавить объявление",
            url = "",
            onClick = { navController.navigate(NavPath.ADDINGANNOUNCEMENTSCREEN.name) }
        )
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.document_icon),
            title = "Блог",
            url = "",
            onClick = { navController.navigate(NavPath.BLOGPAGE.name) }
        )
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.notification_icon),
            title = "Уведомления",
            url = "",
            onClick = { navController.navigate(NavPath.NOTIFICATIONSSCREEN.name) }

        )
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.my_listings_icon),
            title = "Мои объявления",
            url = "",
            onClick = { navController.navigate(NavPath.MYANNOUNCEMENTSCREEN.name) }

        )
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.my_contracts_icon),
            title = "Мои договоры",
            url = "",
            onClick = { navController.navigate(NavPath.ADDINGANNOUNCEMENTSCREEN.name) }
        )
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.my_booking_icon),
            title = "Мои бронирования",
            url = "",
            onClick = { navController.navigate(NavPath.BLOGPAGE.name) }

        )
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.link_icon),
            title = "Моя реферальная ссылка",
            url = "",
            onClick = { navController.navigate(NavPath.BLOGPAGE.name) }

        )
        ProjectAbout(navController = navController)
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                openBottomSheet = true
            }
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.exit_icon),
                contentDescription = null,
                tint = Color(0xff566982),
                modifier = Modifier
                    .size(22.dp)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Выйти",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff010101),
                fontWeight = FontWeight.W700,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xff474B57),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (openBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                contentColor = Color.White,
                containerColor = Color.White,
                sheetState = bottomState,
                onDismissRequest = { openBottomSheet = false },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                        .height(220.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Выйти",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700,
                            color = DefaultBlack
                        )
                    }
                    Text(
                        text = "Вы действительно хотите выйти из аккаунта?",
                        fontSize = 16.sp,
                        color = DefaultGray,
                        fontWeight = FontWeight.W400,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    SaveButton(
                        text = "Да, я хочу выйти",
                        onClick = {
                            authViewModel.logout()
                        }
                    )
                    CustomButtonForEdit(
                        text = "Отмена",
                        onClick = { openBottomSheet = false},
                        colorList = listColor,
                        colorBrush = gradientBrush
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector?, title: String, url: String, onClick: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                } else {
                    onClick()
                }
            }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xff566982),
                modifier = Modifier
                    .size(22.dp)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xff474B57),
            modifier = Modifier.size(30.dp)
        )
    }
}

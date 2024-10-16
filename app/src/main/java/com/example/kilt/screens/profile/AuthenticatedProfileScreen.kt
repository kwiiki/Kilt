package com.example.kilt.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.kilt.R
import com.example.kilt.data.authentification.BioCheckOTPResult
import com.example.kilt.data.authentification.CheckOtpResult
import com.example.kilt.data.authentification.User
import com.example.kilt.viewmodels.AuthViewModel

//@Composable
//fun AuthenticatedProfileScreen(navController: NavHostController,authViewModel: AuthViewModel,user: User?) {
//
//    val bioCheckOTPResult by authViewModel.checkOtpResult
//    val checkOtpResult by authViewModel.checkOtpResult
//
//    val user = (checkOtpResult as? CheckOtpResult.Success)?.user
//        ?: (bioCheckOTPResult as? BioCheckOTPResult.Success)?.user
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//    ) {
//        Spacer(modifier = Modifier.height(24.dp))
//        Text(
//            text = "Профиль",
//            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
//            fontSize = 36.sp,
//            color = Color.Black,
//            modifier = Modifier.padding(vertical = 16.dp),
//            fontWeight = FontWeight.W800
//
//        )
//        UserProfileSection(authViewModel = authViewModel,user)
//        Spacer(modifier = Modifier.height(16.dp))
//        BalanceSection(user)
//        Spacer(modifier = Modifier.height(16.dp))
//        MenuSection()
//        Spacer(modifier = Modifier.height(16.dp))
//        AboutSection()
//    }
//}
//
//@Composable
//fun UserProfileSection(authViewModel: AuthViewModel, user: User?) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
//            .height(113.dp)
//            .padding(16.dp)
//            .clickable {  },
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        if (user?.photo != null) {
//            Image(
//                painter = rememberAsyncImagePainter(model = user.photo),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(83.dp)
//                    .background(color = Color.Transparent, RoundedCornerShape(12.dp))
//            )
//        } else {
//            Image(
//                painter = painterResource(id = R.drawable.non_image),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(83.dp)
//                    .background(color = Color.Transparent, RoundedCornerShape(12.dp))
//            )
//        }
//        Spacer(modifier = Modifier.width(16.dp))
//        Column {
//            Text(
//                "${user?.firstname} ${user?.lastname}",
//                fontWeight = FontWeight.W700,
//                fontSize = 20.sp,
//                color = Color(0xff010101)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
//            val output = regex.replace(user!!.phone, "$1 $2 $3 $4 $5")
//            Text(output, color = Color(0xff010101), fontSize = 16.sp)
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        Icon(
//            Icons.AutoMirrored.Filled.KeyboardArrowRight,
//            contentDescription = null,
//            tint = Color(0xFF566982),
//            modifier = Modifier.size(30.dp)
//        )
//    }
//}
//
//@Composable
//fun BalanceSection(user:User?) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(75.dp)
//            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp)),
//        verticalAlignment = Alignment.CenterVertically // выравнивание по центру
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Баланс:", fontSize = 14.sp, fontWeight = FontWeight.W500)
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.kilt_money),
//                    contentDescription = null,
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("5 000", fontSize = 16.sp, fontWeight = FontWeight.W700)
//            }
//        }
//        Spacer(modifier = Modifier.weight(1f)) // используем weight для создания пространства между колонками
//
//        TextButton(onClick = { /* действие пополнить баланс */ }) {
//            Text("Пополнить баланс", color = Color(0xFF3F4FE0), fontSize = 16.sp)
//        }
//        Spacer(
//            modifier = Modifier
//                .width(1.dp)
//                .fillMaxHeight()
//                .background(color = Color(0xffDBDFE4))
//        )
//        Image(
//            painter = painterResource(id = R.drawable.question_image),
//            contentDescription = null,
//            modifier = Modifier
//                .size(58.dp)
//                .padding(horizontal = 20.dp)
//                .align(Alignment.CenterVertically) // выравнивание по центру
//        )
//    }
//}
//@Composable
//fun MenuSection() {
//    val menuItems = listOf(
//        MenuItem("Добавить объявление", Icons.Default.Add),
//        MenuItem("Блог", icon = ImageVector.vectorResource(id = R.drawable.document_icon)),
//        MenuItem("Push уведомления", icon = ImageVector.vectorResource(id = R.drawable.notification_icon))
//    )
//
//    menuItems.forEach { item ->
//        MenuItemRow(item)
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//}
//@Composable
//fun MenuItemRow(item: MenuItem) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { /* действие при нажатии на элемент меню */ }
//            .padding(vertical = 12.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        MenuItemIcon(item.icon)
//        Spacer(modifier = Modifier.width(14.dp))
//        Text(item.title, fontWeight = FontWeight.W700, fontSize = 16.sp)
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        Icon(
//            Icons.AutoMirrored.Filled.KeyboardArrowRight,
//            contentDescription = null,
//            tint = Color(0xff9096A6),
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}
//@Composable
//fun MenuItemIcon(icon: ImageVector) {
//    when (icon) {
//        Icons.Default.Add -> {
//            Icon(
//                icon,
//                contentDescription = null,
//                tint = Color(0xff9096A6),
//                modifier = Modifier.size(24.dp)
//            )
//        }
//        else -> {
//            Spacer(modifier = Modifier.width(3.dp))
//            Icon(
//                icon,
//                contentDescription = null,
//                tint = Color(0xff9096A6),
//                modifier = Modifier
//                    .size(19.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun AboutSection() {
//    val aboutItems = listOf(
//        "Партнеры",
//        "О приложении",
//        "Мои объявления",
//        "Мои договоры",
//        "Связаться с нами",
//        "Правила сайта",
//        "Пользовательское соглашение"
//    )
//
//    Text("О проекте", fontWeight = FontWeight.W700, fontSize = 24.sp)
//
//    Spacer(modifier = Modifier.height(8.dp))
//
//    aboutItems.forEach { title ->
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()f
//                .clickable { /* действие при нажатии на пункт */ }
//                .padding(vertical = 12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(title, fontSize = 16.sp, fontWeight = FontWeight.W700)
//            Spacer(modifier = Modifier.weight(1f))
//            Icon(
//                Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                contentDescription = null,
//                tint = Color(0xff9096A6)
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//}
//
//data class MenuItem(val title: String, val icon: ImageVector)
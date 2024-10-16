package com.example.kilt.screens.profile

import android.util.Log
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
import androidx.compose.runtime.collectAsState
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
import com.example.kilt.R
import com.example.kilt.data.authentification.User
import com.example.kilt.data.authentification.UserWithMetadata
import com.example.kilt.enums.UserType
import com.example.kilt.screens.profile.login.LoginButton
import com.example.kilt.screens.profile.registration.RegistrationButton
import com.example.kilt.viewmodels.AuthViewModel


@Composable
fun ProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val userWithMetadata by authViewModel.user.collectAsState(initial = null)
    val isUserAuthenticated by authViewModel.isUserAuthenticated
    val isUserIdentified by authViewModel.isUserIdentified
    Log.d("ProfileScreen", "isUserAuthenticated: $isUserAuthenticated")
    Log.d("AuProfile5", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.firstname}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Профиль",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 36.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.W800
        )

        if (isUserAuthenticated) {
            Log.d("AuProfile6", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.firstname}")
            AuthenticatedProfileScreen(navController, authViewModel, userWithMetadata, isUserIdentified)
        } else {
            Log.d("AuProfile7", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.firstname}")
            UnauthenticatedProfileScreen(navController)
        }
        Text(
            text = "Выйти",
            fontSize = 18.sp,
            color = Color.Red,
            modifier = Modifier.clickable { authViewModel.logout() }
        )
    }
}

@Composable
fun AuthenticatedProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata?,
    isUserIdentified: Boolean
) {
    Log.d("AuProfile0", "User Type: ${userWithMetadata?.user?.user_type}")
    Log.d("AuProfile", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.firstname}")

    if(userWithMetadata?.user?.user_type == "" || userWithMetadata?.user?.user_type == UserType.AGENCY.value) {
        if (isUserIdentified) {
            UserProfileSection(authViewModel = authViewModel, userWithMetadata.user)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Баланс:", fontSize = 14.sp, fontWeight = FontWeight.W500)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.kilt_money),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${userWithMetadata?.bonus}", fontSize = 16.sp, fontWeight = FontWeight.W700)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { }) {
                    Text("Пополнить баланс", color = Color(0xFF3F4FE0), fontSize = 16.sp)
                }
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(color = Color(0xffDBDFE4))
                )
                Image(
                    painter = painterResource(id = R.drawable.question_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(58.dp)
                        .padding(horizontal = 20.dp)
                        .align(Alignment.CenterVertically)
                )
            }

        } else {
            UnidentifiedAgency(authViewModel = authViewModel, userWithMetadata.user)

        }
    } else {
        UserProfileSection(authViewModel = authViewModel, userWithMetadata?.user)
    }
    Spacer(modifier = Modifier.height(16.dp))
    MenuSection()
    Spacer(modifier = Modifier.height(16.dp))
    AboutSection()
}

@Composable
fun UnauthenticatedProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Войдите чтобы расширить возможности и следить за поисками",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(8.dp))
        LoginButton(navController, Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        RegistrationButton(navController, Modifier.padding(horizontal = 8.dp))
    }
    Spacer(modifier = Modifier.height(24.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingItem(
            icon = ImageVector.vectorResource(id = R.drawable.document_icon), // Replace with your custom icon
            title = "Блог",
        )
        Text(
            text = "О проекте",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp),
            fontSize = 24.sp
        )
        SettingItem(
            icon = null,
            title = "Политика конфиденциальности"
        )
        SettingItem(
            icon = null,
            title = "Пользовательское соглашение"
        )
        SettingItem(
            icon = null,
            title = "Связаться с нами"
        )
        SettingItem(
            icon = null,
            title = "Правила"
        )
    }
}

@Composable
fun MenuSection() {
    val menuItems = listOf(
        MenuItem("Добавить объявление", Icons.Default.Add),
        MenuItem("Блог", icon = ImageVector.vectorResource(id = R.drawable.document_icon)),
        MenuItem(
            "Уведомления",
            icon = ImageVector.vectorResource(id = R.drawable.notification_icon)
        )
    )
    menuItems.forEach { item ->
        MenuItemRow(item)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun SettingItem(icon: ImageVector?, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Implement click handling */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xff566982)
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
            tint = Color.Gray
        )
    }
}

@Composable
fun UserProfileSection(authViewModel: AuthViewModel, user: User?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
            .height(113.dp)
            .padding(16.dp)
            .clickable { },
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
            val firstName = user?.firstname ?: "Имя"
            val lastName = user?.lastname ?: "Фамилия"
            val phoneNumber = user?.phone ?: "Нет телефона"
            val userTypeText = when (user?.user_type) {
                "owner" -> UserType.OWNER.ruText
                "specialist" -> UserType.AGENT.ruText
                "agency" -> UserType.AGENCY.ruText
                else -> ""
            }
            Row(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.chield_check_fill),
                    contentDescription = null,
                    modifier = Modifier
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
                "$firstName $lastName",
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                color = Color(0xff010101)
            )
            Spacer(modifier = Modifier.height(4.dp))
            val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
            val output = regex.replace(phoneNumber, "$1 $2 $3 $4 $5")
            Text(output, color = Color(0xff010101), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF566982),
            modifier = Modifier.size(30.dp)
        )
    }
}
@Composable
fun UnidentifiedAgency(authViewModel: AuthViewModel, user: User?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
            .height(113.dp)
            .padding(16.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.succesful_image),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .background(color = Color.Transparent, RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Пройти идентификацию",
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            color = Color(0xff010101)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF566982),
            modifier = Modifier.size(30.dp)
        )
    }
}
@Composable
fun AboutSection() {
    val aboutItems = listOf(
        "Партнеры",
        "О приложении",
        "Мои объявления",
        "Мои договоры",
        "Связаться с нами",
        "Правила сайта",
        "Пользовательское соглашение"
    )
    Text("О проекте", fontWeight = FontWeight.W700, fontSize = 24.sp)
    Spacer(modifier = Modifier.height(8.dp))
    aboutItems.forEach { title ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* действие при нажатии на пункт */ }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.W700)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xff9096A6)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
@Composable
fun MenuItemRow(item: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* действие при нажатии на элемент меню */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItemIcon(item.icon)
        Spacer(modifier = Modifier.width(14.dp))
        Text(item.title, fontWeight = FontWeight.W700, fontSize = 16.sp)

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xff9096A6),
            modifier = Modifier.size(24.dp)
        )
    }
}
@Composable
fun MenuItemIcon(icon: ImageVector) {
    when (icon) {
        Icons.Default.Add -> {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xff9096A6),
                modifier = Modifier.size(24.dp)
            )
        }
        else -> {
            Spacer(modifier = Modifier.width(3.dp))
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xff9096A6),
                modifier = Modifier
                    .size(19.dp)
            )
        }
    }
}

data class MenuItem(val title: String, val icon: ImageVector)
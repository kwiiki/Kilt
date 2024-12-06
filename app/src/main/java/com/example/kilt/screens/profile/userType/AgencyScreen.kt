package com.example.kilt.screens.profile.userType

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.models.authentification.User
import com.example.kilt.models.authentification.UserWithMetadata
import com.example.kilt.utills.enums.IdentificationTypes
import com.example.kilt.utills.enums.UserType
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.presentation.editprofile.gradientBrush
import com.example.kilt.presentation.editprofile.listColor
import com.example.kilt.presentation.profile.viewmodel.ProfileViewModel
import com.example.kilt.utills.imageKiltUrl
import com.example.kilt.presentation.login.viewModel.AuthViewModel

val listColor1 = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
@Composable
fun AgencyScreen(
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata?,
    user: User,
    navController: NavHostController,
    profileViewModel: ProfileViewModel
) {
    val currentUser by authViewModel.currentUser
    val isUserIdentified by authViewModel.isUserIdentified
    val userModerationStatus by profileViewModel.moderationStatus

    LaunchedEffect(Unit) {
        authViewModel.checkVerificationStatus(currentUser?.user?.id.toString())
        profileViewModel.checkModerationStatus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Log.d("AgencyScreen", "AgencyScreen: currentUser=${currentUser?.user?.agency_verification_status}, isUserIdentified=$isUserIdentified, moderationStatus=$userModerationStatus")

        when {
            currentUser?.user?.agency_verification_status == 2 && !userModerationStatus -> {
                IdentifiedUser(navController = navController, user)
                Spacer(modifier = Modifier.height(16.dp))
                BalanceSection(userWithMetadata)
            }
            currentUser?.user?.agency_verification_status == 2 && userModerationStatus -> {
                IsModeration()
            }
            else -> {
                IsIdentified(navController = navController, isUserIdentified)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        UserMenu(navController = navController, authViewModel)
    }
}
@Composable
fun BalanceSection(userWithMetadata: UserWithMetadata?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xFFF7F8FB), shape = RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(text = "Баланс:", fontSize = 16.sp, fontWeight = FontWeight.W500)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${userWithMetadata?.bonus ?: 0}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = listColor,
                            tileMode = TileMode.Mirror
                        )
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.kilt_money),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(end = 16.dp)) {
            CustomButtonProfiles(
                text = "Пополнить баланс",
                onClick = {},
                colorList = listColor1,
                colorBrush = gradientBrush
            )
        }
    }
}
@Composable
fun CustomButtonProfiles(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorList: List<Color>,
    colorBrush: Brush
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = colorBrush,
                shape = RoundedCornerShape(12.dp)
            )
            .height(45.dp)
            .width(54.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = colorList,
                            tileMode = TileMode.Mirror
                        ),
                    ), fontWeight = FontWeight.W700
                )
            }
        }
    }
}

@Composable
fun IdentifiedUser(navController: NavHostController, user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
            .clickable { navController.navigate(NavPath.USERPROFILESCREEN.name) },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Log.d("userPhoto", "IdentifiedUser: ${user.photo}")
            if(user.photo == "") {
                Image(
                    painter = painterResource(id = R.drawable.non_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = Color.Transparent, RoundedCornerShape(12.dp))
                )
            } else {
                AsyncImage(
                    model = "$imageKiltUrl${user.photo}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = Color.Transparent, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                val firstName = user.firstname
                val lastName = user.lastname
                val userTypeText = when (user.user_type) {
                    "owner" -> UserType.OWNER.ruText
                    "specialist" -> UserType.AGENT.ruText
                    "agency" -> UserType.AGENCY.ruText
                    else -> ""
                }
                Row(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(vertical = 4.dp)
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
}

@Composable
fun IsModeration() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
            .height(113.dp)
            .padding(16.dp)
            .clickable(enabled = false) { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.succesful_image),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = "Ваши изменения на проверке",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 24.sp,
            color = Color(0xff010101),
            modifier = Modifier.width(160.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(30.dp)
        )
    }
}
@Composable
fun IsIdentified(navController: NavHostController, isUserIdentified: IdentificationTypes) {
    val isEnabled = isUserIdentified != IdentificationTypes.IsIdentified
    val displayText = if (isEnabled) "Пройти идентификацию" else "Запрос на идентификацию отправлен"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
            .height(113.dp)
            .padding(16.dp)
            .clickable(enabled = isEnabled) {
                navController.navigate(NavPath.IDENTIFICATIONSCREEN.name)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.succesful_image),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Transparent, RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = displayText,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 24.sp,
            color = Color(0xff010101),
            modifier = Modifier.width(160.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = if (isEnabled) Color(0xFF566982) else Color.Gray,
            modifier = Modifier.size(30.dp)
        )
    }
}
package com.example.kilt.screens.profile.userType

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.kilt.models.authentification.User
import com.example.kilt.models.authentification.UserWithMetadata
import com.example.kilt.enums.IdentificationTypes
import com.example.kilt.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun AgencyScreen(
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata?,
    user: User,
    navController: NavHostController
) {
    val currentUser by authViewModel.currentUser
    val isUserIdentified by authViewModel.isUserIdentified
    LaunchedEffect(Unit) {
        authViewModel.checkVerificationStatus(currentUser?.user?.id.toString())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (isUserIdentified.value == 2) {
            IdentifiedUser(navController = navController,user)
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
                        Text(
                            "${userWithMetadata?.bonus}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W700
                        )
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
            IsIdentified(navController = navController, isUserIdentified)
        }
        Spacer(modifier = Modifier.height(24.dp))
        UserMenu(navController = navController)
        Text(text = "Выйти",
            fontSize = 18.sp,
            color = Color.Red,
            modifier = Modifier.clickable { authViewModel.logout() }
        )
    }
}

@Composable
fun IdentifiedUser(navController: NavHostController,user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(12.dp))
            .height(113.dp)
            .padding(16.dp)
            .clickable { navController.navigate(NavPath.AGENCYPROFILESCREEN.name)},
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
            val firstName = user.firstname
            val lastName = user.lastname
            val phoneNumber = user.phone
            val userTypeText = when (user.user_type) {
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
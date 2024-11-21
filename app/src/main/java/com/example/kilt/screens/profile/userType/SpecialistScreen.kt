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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.models.authentification.User
import com.example.kilt.models.authentification.UserWithMetadata
import com.example.kilt.utills.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.presentation.profile.viewmodel.ProfileViewModel
import com.example.kilt.utills.imageKiltUrl
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun SpecialistScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata,
    user: User,
    profileViewModel: ProfileViewModel,
) {
    val userModerationStatus by profileViewModel.moderationStatus
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when {
            !userModerationStatus -> {
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
                        if (user.phone.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.non_image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(
                                        color = Color.Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
                            )
                        } else {
                            AsyncImage(
                                model = "$imageKiltUrl${user.photo}",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(
                                        color = Color.Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
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
            else -> {
                IsModeration()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        BalanceSection(userWithMetadata = userWithMetadata)
        Spacer(modifier = Modifier.height(24.dp))
        UserMenu(navController = navController, authViewModel = authViewModel)
    }
}

package com.example.kilt.screens.profile.userType

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.kilt.utills.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.presentation.editprofile.gradientBrush
import com.example.kilt.presentation.editprofile.listColor
import com.example.kilt.presentation.profile.viewmodel.ProfileViewModel
import com.example.kilt.utills.imageKiltUrl
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun OwnerScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata,
    user: User,
    profileViewModel: ProfileViewModel,
) {
    val userModerationStatus by profileViewModel.moderationStatus
    Log.d("userId", "OwnerScreen: ${user.id}")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when {
            !userModerationStatus -> {
                IdentifiedUser(navController = navController, user)
                Spacer(modifier = Modifier.height(16.dp))
                BalanceSection(userWithMetadata)
            }
            else-> {
                IsModeration()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        UserMenu(navController = navController, authViewModel = authViewModel)
    }
}
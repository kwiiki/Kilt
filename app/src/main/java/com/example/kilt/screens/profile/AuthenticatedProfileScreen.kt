package com.example.kilt.screens.profile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.kilt.models.authentification.UserWithMetadata
import com.example.kilt.enums.UserType
import com.example.kilt.screens.profile.userType.AgencyScreen
import com.example.kilt.screens.profile.userType.OwnerScreen
import com.example.kilt.screens.profile.userType.SpecialistScreen
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun AuthenticatedProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata?,
) {
    val currentUser by authViewModel.currentUser
    Log.d("user_type111", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.user_type}")
    Log.d("user_type111", "AuthenticatedProfileScreen: ${userWithMetadata?.user}")
    Log.d("user_type111", "AuthenticatedProfileScreen: ${currentUser?.user?.id}")
    LaunchedEffect(Unit) {
        authViewModel.checkVerificationStatus(currentUser?.user?.id.toString())
        authViewModel.refreshUserData(currentUser?.user?.id.toString())
    }
    when (userWithMetadata?.user?.user_type) {
        UserType.AGENCY.value -> {
            AgencyScreen(authViewModel = authViewModel, userWithMetadata,userWithMetadata.user,navController = navController)
        }
        UserType.OWNER.value -> {
            OwnerScreen(navController = navController,authViewModel = authViewModel, userWithMetadata,userWithMetadata.user)
        }
        UserType.AGENT.value -> {
            SpecialistScreen(navController = navController,authViewModel = authViewModel, userWithMetadata,userWithMetadata.user)
        }
    }
}
package com.example.kilt.screens.profile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.kilt.data.authentification.UserWithMetadata
import com.example.kilt.enums.IdentificationTypes
import com.example.kilt.enums.UserType
import com.example.kilt.screens.profile.userType.IdentifiedAgency
import com.example.kilt.screens.profile.userType.OwnerScreen
import com.example.kilt.screens.profile.userType.SpecialistScreen
import com.example.kilt.screens.profile.userType.UnidentifiedAgency
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun AuthenticatedProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userWithMetadata: UserWithMetadata?,
) {
    val isUserIdentified by authViewModel.isUserIdentified
    Log.d("user_type111", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.user_type}")
    Log.d("user_type111", "AuthenticatedProfileScreen: ${userWithMetadata?.user}")
    if(userWithMetadata?.user?.user_type == "" || userWithMetadata?.user?.user_type == UserType.AGENCY.value) {
        if (isUserIdentified.value == 2) {
         IdentifiedAgency(authViewModel = authViewModel, userWithMetadata,userWithMetadata.user,navController = navController)
            Log.d("userId", "AuthenticatedProfileScreen: ${userWithMetadata.user.id}")
        } else {
            UnidentifiedAgency(authViewModel = authViewModel, userWithMetadata.user,navController = navController)
        }
    } else if (userWithMetadata?.user?.user_type == UserType.OWNER.value) {
        OwnerScreen(navController = navController,authViewModel = authViewModel, userWithMetadata,userWithMetadata.user)
    } else if (userWithMetadata?.user?.user_type == UserType.AGENT.value) {
        SpecialistScreen(navController = navController,authViewModel = authViewModel, userWithMetadata,userWithMetadata.user)
    }
}
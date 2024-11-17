@file:OptIn(ExperimentalMaterialApi::class)

package com.example.kilt.presentation.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.enums.UserType
import com.example.kilt.presentation.profile.viewmodel.ProfileViewModel
import com.example.kilt.screens.profile.userType.AgencyScreen
import com.example.kilt.screens.profile.userType.OwnerScreen
import com.example.kilt.screens.profile.userType.SpecialistScreen
import com.example.kilt.screens.profile.userType.UnAuthenticatedProfileScreen
import com.example.kilt.viewmodels.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    val userWithMetadata by authViewModel.user.collectAsState(initial = null)
    val isUserAuthenticated by authViewModel.isUserAuthenticated
    val scrollState = rememberScrollState()
    val currentUser by authViewModel.currentUser
    var refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            currentUser?.user?.id?.let { userId ->
                authViewModel.refreshUserData(userId.toString())
            }
            profileViewModel.checkModerationStatus()
            refreshing = false
        }
    )

    LaunchedEffect(Unit) {
        currentUser?.user?.id?.let { userId ->
            authViewModel.refreshUserData(userId.toString())
            authViewModel.checkVerificationStatus(userId.toString())
        }
    }
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
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

            if (isUserAuthenticated && userWithMetadata != null && currentUser != null) {
                Log.d("user_type111", "AuthenticatedProfileScreen: ${userWithMetadata?.user?.user_type}")
                when (currentUser!!.user.user_type) {
                    UserType.AGENCY.value -> {
                        AgencyScreen(
                            authViewModel = authViewModel,
                            userWithMetadata = userWithMetadata,
                            user = userWithMetadata!!.user,
                            navController = navController,
                            profileViewModel = profileViewModel
                        )
                    }
                    UserType.OWNER.value -> {
                        OwnerScreen(
                            navController = navController,
                            authViewModel = authViewModel,
                            userWithMetadata = userWithMetadata!!,
                            user = userWithMetadata!!.user,
                            profileViewModel = profileViewModel
                        )
                    }
                    UserType.AGENT.value -> {
                        SpecialistScreen(
                            navController = navController,
                            authViewModel = authViewModel,
                            userWithMetadata = userWithMetadata!!,
                            user = userWithMetadata!!.user,
                            profileViewModel = profileViewModel
                        )
                    }
                }
            } else {
                UnAuthenticatedProfileScreen(navController)
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
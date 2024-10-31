package com.example.kilt.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.screens.profile.userType.UnAuthenticatedProfileScreen
import com.example.kilt.viewmodels.AuthViewModel


@Composable
fun ProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val userWithMetadata by authViewModel.user.collectAsState(initial = null)
    val isUserAuthenticated by authViewModel.isUserAuthenticated
    val scrollState = rememberScrollState()
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
        if (isUserAuthenticated) {
            AuthenticatedProfileScreen(navController, authViewModel, userWithMetadata)
        } else {
            UnAuthenticatedProfileScreen(navController)
        }
    }
}


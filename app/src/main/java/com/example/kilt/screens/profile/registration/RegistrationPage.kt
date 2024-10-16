package com.example.kilt.screens.profile.registration

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.data.authentification.BioOtpResult
import com.example.kilt.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.screens.searchpage.homedetails.gradient
import com.example.kilt.viewmodels.AuthViewModel

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RegistrationPage(navController: NavHostController, authViewModel: AuthViewModel) {

    val registrationUiState by authViewModel.registrationUiState
    val selectedUserType = registrationUiState.userType
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }
    val bioOtpResult by authViewModel.bioOtpResult
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    LaunchedEffect(bioOtpResult) {
        bioOtpResult?.let {
            when (it) {
                is BioOtpResult.Success -> {
                    navController.navigate(NavPath.PROFILE.name)
                }
                is BioOtpResult.Failure -> {
                    errorMessage = it.message
                    showError = true
                }
                is BioOtpResult.RegisteredUser -> {
                    navController.navigate(NavPath.ENTERFOURCODEPAGE.name)
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Row {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .clickable { navController.popBackStack() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Укажите тип аккаунта",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Выберите один вариант",
                    fontSize = 16.sp,
                    color = Color(0xff566982),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp, vertical = 140.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { authViewModel.updateUserType(UserType.OWNER)}
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = UserType.OWNER.ruText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = if (selectedUserType == UserType.OWNER) Color(0xFF3F4FE0) else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (selectedUserType == UserType.OWNER) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                            contentDescription = "Selected",
                            tint = Color(0xFF3F4FE0),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ){ authViewModel.updateUserType(UserType.AGENT) }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = UserType.AGENT.ruText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = if (selectedUserType == UserType.AGENT) Color(0xFF3F4FE0) else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (selectedUserType == UserType.AGENT) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                            contentDescription = "Selected",
                            tint = Color(0xFF3F4FE0),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ){ authViewModel.updateUserType(UserType.AGENCY) }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = UserType.AGENCY.ruText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = if (selectedUserType == UserType.AGENCY) Color(0xFF3F4FE0) else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (selectedUserType == UserType.AGENCY) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_icon),
                            contentDescription = "Selected",
                            tint = Color(0xFF3F4FE0),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 85.dp)
                .padding(horizontal = 30.dp),
        ) {
            Text(
                text = "При регистрации аккаунта я даю согласие на обработку своих персональных данных, принимаю условия пользовательского соглашения и Политики конфиденциальности.",
                fontSize = 12.sp,
                lineHeight = 20.sp,
                color = Color(0xff566982),
                fontWeight = FontWeight.W700,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            OutlinedButton(
                onClick = {
                    when (selectedUserType) {
                        UserType.OWNER -> {
                            navController.navigate(NavPath.OWNERPAGE.name)
                        }
                        UserType.AGENT -> {
                            navController.navigate(NavPath.OWNERPAGE.name)
                        }
                        UserType.AGENCY -> {
                            navController.navigate(NavPath.AGENCYPAGE.name)
                        }
                    }
                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(gradient, RoundedCornerShape(12.dp))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Продолжить",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewRegistrationPage() {
    RegistrationPage(navController = rememberNavController(), authViewModel = hiltViewModel())
}

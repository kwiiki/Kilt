package com.example.kilt.presentation.editprofile.changeusertypebottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.navigation.NavPath
import com.example.kilt.presentation.editprofile.components.CustomButtonForEdit
import com.example.kilt.presentation.editprofile.components.SaveButton
import com.example.kilt.presentation.editprofile.gradientBrush
import com.example.kilt.presentation.editprofile.listColor
import com.example.kilt.presentation.editprofile.viewmodel.EditProfileViewModel
import com.example.kilt.presentation.theme.DefaultBlack
import com.example.kilt.presentation.theme.DefaultGray

@Composable
fun ChangeUserTypeBottomSheet(
    editProfileViewModel: EditProfileViewModel,
    onClick: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .height(220.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Стать риелтором",
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = DefaultBlack
            )
        }
        Text(
            text = "Вы уверены, что хотите переключиться на аккаунт риелтора? Это действие невозможно отменить",
            fontSize = 16.sp,
            color = DefaultGray,
            fontWeight = FontWeight.W400,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        SaveButton(
            text = "Переключиться",
            onClick = {
                editProfileViewModel.changeUserType()
                navController.navigate(NavPath.PROFILE.name)
            }
        )
        CustomButtonForEdit(
            text = "Отмена",
            onClick = onClick,
            colorList = listColor,
            colorBrush = gradientBrush
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

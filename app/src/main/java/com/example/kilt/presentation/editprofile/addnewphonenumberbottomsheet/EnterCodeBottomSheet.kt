@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.viewmodel.AddNewPhoneNumberViewModel
import com.example.kilt.presentation.editprofile.components.CustomButtonForEdit
import com.example.kilt.presentation.editprofile.components.SaveButton
import com.example.kilt.presentation.editprofile.gradientBrush
import com.example.kilt.presentation.editprofile.listColor

@Composable
fun EnterCodeBottomSheet(
    addNewPhoneNumberViewModel: AddNewPhoneNumberViewModel,
    onDismiss: () -> Unit
) {
    val uiState = addNewPhoneNumberViewModel.editProfileUiState.value
    val showError = addNewPhoneNumberViewModel.showError.value
    val errorMessage = addNewPhoneNumberViewModel.errorMessage.value
    val isOTPVerified = addNewPhoneNumberViewModel.isOTPVerified.value
    val isUserCreated = addNewPhoneNumberViewModel.isUserCreated.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .height(240.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Введите код из смс",
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            color = Color.Black
        )
        OutlinedTextField(
            value = uiState.code,
            onValueChange = { newCode ->
                if (newCode.length <= 4) {
                    addNewPhoneNumberViewModel.updateForFourCode(newCode)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (showError) Color.Red else Color(0xFFcfcfcf),
                focusedBorderColor = if (showError) Color.Red else Color(0xFFcfcfcf),
                cursorColor = Color(0xFFDBDFE4)
            ),
            label = {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("SMS код")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("*")
                        }
                    }
                )
            }
        )
        if (showError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        SaveButton(
            text = "Сохранить",
            onClick = {
                addNewPhoneNumberViewModel.userFindByOTP(uiState.code)
                addNewPhoneNumberViewModel.universalUserCreate()
                if (isOTPVerified && isUserCreated) {
                    addNewPhoneNumberViewModel.clear()
                }
            }
        )
        CustomButtonForEdit(text = "Вернутся назад",
            onClick = { onDismiss() },
            colorList = listColor,
            colorBrush = gradientBrush)
    }
}
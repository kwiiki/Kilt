package com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.viewmodel.AddNewPhoneNumberViewModel
import com.example.kilt.presentation.editprofile.components.SaveButton
import com.example.kilt.screens.profile.login.PhoneNumberTextField

@Composable
fun AddNewPhoneNumber(addNewPhoneNumberViewModel: AddNewPhoneNumberViewModel, onClick: () -> Unit) {
    val uiState = addNewPhoneNumberViewModel.editProfileUiState.value
    val showError = addNewPhoneNumberViewModel.showError.value
    val errorMessage = addNewPhoneNumberViewModel.errorMessage.value
    val isLoading = addNewPhoneNumberViewModel.isLoading.value
    val isPhoneAddedSuccessfully = addNewPhoneNumberViewModel.isPhoneAddedSuccessfully.value
    val focusManager = LocalFocusManager.current


    if (isPhoneAddedSuccessfully) {
        EnterCodeBottomSheet(addNewPhoneNumberViewModel,onClick)

    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(180.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Text(
                    text = "Добавить номер телефона",
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                IconButton(onClick = { onClick() }) {
                    Icons.Default.Close
                }

            }
            PhoneNumberTextField(
                value = uiState.secondPhoneNumber,
                onValueChange = {
                    addNewPhoneNumberViewModel.updatePhoneNumber("+7$it")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                focusManager = focusManager,
                showError = showError
            )

            if (showError) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            SaveButton(
                onClick = {
                    if (uiState.secondPhoneNumber.length == 12) {
                        val phone = Phone(uiState.secondPhoneNumber)
                        addNewPhoneNumberViewModel.addPhoneNumber(phone)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
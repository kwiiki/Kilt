package com.example.kilt.presentation.editprofile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.editprofile.model.Phone
import com.example.kilt.domain.editprofile.usecase.AddPhoneUseCase
import com.example.kilt.presentation.editprofile.EditProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val addPhoneUseCase: AddPhoneUseCase
) : ViewModel() {
    var editProfileUiState = mutableStateOf(EditProfileUiState())
        private set

    var isLoading = mutableStateOf(false)
    var showError = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun updatePhoneNumber(newNumber: String) {
        Log.d("newNumber", "updatePhoneNumber: $newNumber")
        editProfileUiState.value = editProfileUiState.value.copy(secondPhoneNumber = newNumber)
    }

    fun addPhoneNumber(phone: Phone) {
        viewModelScope.launch {
            isLoading.value = true
            val result = addPhoneUseCase(phone)
            isLoading.value = false

            Log.d("newNumber1", "addPhoneNumber: $result")
            result.onSuccess { response ->
                if (response.success) {
                    showError.value = false
                    errorMessage.value = ""
                } else {
                    showError.value = true
                    errorMessage.value = if (response.exists == true) {
                        "Данный номер телефона был ранее использован"
                    } else {
                        "Не удалось добавить номер"
                    }
                }
            }.onFailure {
                showError.value = true
                errorMessage.value = it.message ?: "Ошибка добавления номера"
            }
        }
    }
}
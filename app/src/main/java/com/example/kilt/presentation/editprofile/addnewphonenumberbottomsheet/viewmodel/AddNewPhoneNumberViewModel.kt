package com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Create
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Filters
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.AddPhoneUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UniversalUserCreateUseCase
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase.UserFindByOTPUseCase
import com.example.kilt.presentation.editprofile.EditProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddNewPhoneNumberViewModel @Inject constructor(
    private val addPhoneUseCase: AddPhoneUseCase,
    private val userFindByOTPUseCase: UserFindByOTPUseCase,
    private val universalUserCreateUseCase: UniversalUserCreateUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel() {
    var editProfileUiState = mutableStateOf(EditProfileUiState())
        private set

    var isLoading = mutableStateOf(false)
    var showError = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var isPhoneAddedSuccessfully = mutableStateOf(false)
    val isOTPVerified = mutableStateOf(false)
    val isUserCreated = mutableStateOf(false)

    fun updatePhoneNumber(newNumber: String) {
        editProfileUiState.value = editProfileUiState.value.copy(secondPhoneNumber = newNumber)
    }

    fun addPhoneNumber(phone: Phone) {
        viewModelScope.launch {
            isLoading.value = true
            val result = addPhoneUseCase(phone)
            isLoading.value = false
            result.onSuccess { response ->
                if (response.success) {
                    showError.value = false
                    errorMessage.value = ""
                    isPhoneAddedSuccessfully.value = true
                } else {
                    showError.value = true
                    errorMessage.value = if (response.exists == true) {
                        "Номер уже существует"
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

    fun updateForFourCode(newCode: String) {
        editProfileUiState.value = editProfileUiState.value.copy(code = newCode)
        if (newCode.length == 4) {
            userFindByOTP(code = newCode)
        }
    }

    fun userFindByOTP(code: String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = userFindByOTPUseCase(
                Filters(
                    phone = editProfileUiState.value.secondPhoneNumber,
                    code = code
                )
            )
            isLoading.value = false
            result.onSuccess { response ->
                if (response.list.isNotEmpty()) {
                    showError.value = false
                    errorMessage.value = ""
                    isOTPVerified.value = true
                } else {
                    showError.value = true
                    errorMessage.value = "Неверный код"
                }
            }.onFailure {
                showError.value = true
                errorMessage.value = it.message ?: "Ошибка при проверке кода"
            }
        }
    }

    fun universalUserCreate() {
        viewModelScope.launch {
            val create = Create(
                user_id = getUserIdUseCase.execute(),
                phone = editProfileUiState.value.secondPhoneNumber
            )
            clearSecondPhoneNumber()
            Log.d("create", "universalUserCreate: $create")
            isLoading.value = true
            val result = universalUserCreateUseCase(
                create
            )
            isLoading.value = false
            result.onSuccess {
                isUserCreated.value = true
            }.onFailure {
                showError.value = true
                errorMessage.value = it.message ?: "Ошибка при добавлении номера "
            }
        }
    }
    fun clearSecondPhoneNumber(){
        editProfileUiState.value.secondPhoneNumber = ""
    }

    fun clear(){
        isUserCreated.value = false
        isPhoneAddedSuccessfully.value = false
        isOTPVerified.value = false
        editProfileUiState.value.secondPhoneNumber = ""
        editProfileUiState.value.code = ""
    }
}
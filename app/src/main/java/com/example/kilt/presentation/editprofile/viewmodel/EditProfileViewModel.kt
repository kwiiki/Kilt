package com.example.kilt.presentation.editprofile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.singelton.LocationManager
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.editprofile.usecase.GetUserPhoneNumbersUseCase
import com.example.kilt.presentation.editprofile.EditProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    val phoneNumbers = mutableStateOf<List<String>>(emptyList())
    val location = mutableStateOf("")

    private val _uiState = mutableStateOf(EditProfileUiState())
    val uiState: State<EditProfileUiState> = _uiState

    fun uploadDate() {
        viewModelScope.launch {
            getUserUseCase.execute().let { userWithMetadata ->
                _uiState.value = _uiState.value.copy(
                    userAbout = userWithMetadata.user.agent_about,
                    firstname = userWithMetadata.user.firstname,
                    userFullAddress = userWithMetadata.user.agent_full_address,
                    userWorkHour = userWithMetadata.user.agent_working_hours,
                    userCity = userWithMetadata.user.agent_city
                )
            }
        }
    }
    fun loadPhoneNumbers() {
        viewModelScope.launch {
            try {
                val numbers = getUserPhoneNumbersUseCase.invoke()
                phoneNumbers.value = numbers
            } catch (e: Exception) {
                Log.d("phone numbers", "loadPhoneNumbers: Some error")
            } finally {
                Log.d("phone numbers", "loadPhoneNumbers: error")

            }
        }
    }

    fun updateUserAbout(value: String) {
        _uiState.value = _uiState.value.copy(userAbout = value)
    }

    fun updateUserFullAddress(value: String) {
        _uiState.value = _uiState.value.copy(userFullAddress = value)
    }

    fun updateUserWorkHour(value: String) {
        _uiState.value = _uiState.value.copy(userWorkHour = value)
    }
}
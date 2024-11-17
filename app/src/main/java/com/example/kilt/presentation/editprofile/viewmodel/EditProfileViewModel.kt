package com.example.kilt.presentation.editprofile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.choosecity.usecase.GetFullAddressUseCase
import com.example.kilt.domain.choosecity.usecase.LocationSaverUseCase
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.presentation.editprofile.EditProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getFullAddressUseCase: GetFullAddressUseCase,
    private val locationSaverUseCase: LocationSaverUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(EditProfileUiState())
    val uiState: State<EditProfileUiState> = _uiState

    init {
        loadUserData()
        observeFullAddress()
    }

    fun loadUserData() {
        viewModelScope.launch {
            val userWithMetadata = getUserUseCase.execute()
            _uiState.value = _uiState.value.copy(
                userAbout = userWithMetadata?.user?.agent_about ?: "",
                firstname = userWithMetadata?.user?.firstname ?: "",
                userFullAddress = userWithMetadata?.user?.agent_full_address ?: "",
                userWorkHour = userWithMetadata?.user?.agent_working_hours ?: "",
                userCity = _uiState.value.userCity.ifBlank {
                    userWithMetadata?.user?.agent_city ?: ""
                }
            )
        }
    }

    private fun observeFullAddress() {
        viewModelScope.launch {
            getFullAddressUseCase.execute().collect { fullAddress ->
                _uiState.value = _uiState.value.copy(userCity = fullAddress)
            }
        }
    }

    fun updateUserCity(value: String) {
        _uiState.value = _uiState.value.copy(userCity = value)
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
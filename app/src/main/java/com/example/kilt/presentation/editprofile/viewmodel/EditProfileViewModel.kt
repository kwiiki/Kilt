package com.example.kilt.presentation.editprofile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.choosecity.usecase.GetFullAddressUseCase
import com.example.kilt.domain.choosecity.usecase.LocationSaverUseCase
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.editprofile.model.UpdatedUser
import com.example.kilt.domain.editprofile.usecase.ChangeUserTypeUseCase
import com.example.kilt.domain.editprofile.usecase.UserUpdateUseCase
import com.example.kilt.models.authentification.User
import com.example.kilt.presentation.editprofile.EditProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getFullAddressUseCase: GetFullAddressUseCase,
    private val locationSaverUseCase: LocationSaverUseCase,
    private val changeUserTypeUseCase: ChangeUserTypeUseCase,
    private val userUpdateUseCase: UserUpdateUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(EditProfileUiState())
    val uiState: State<EditProfileUiState> = _uiState

    private var originalUser: User? = null
    private val _isSaveEnabled = mutableStateOf(false)
    val isSaveEnabled: State<Boolean> = _isSaveEnabled

    init {
        loadUserData()
        observeFullAddress()
    }


    fun loadUserData() {
        viewModelScope.launch {
            val userWithMetadata = getUserUseCase.execute()
            originalUser = userWithMetadata?.user
            _uiState.value = _uiState.value.copy(
                userAbout = userWithMetadata?.user?.agent_about ?: "",
                firstname = userWithMetadata?.user?.firstname ?: "",
                userFullAddress = userWithMetadata?.user?.agent_full_address ?: "",
                userWorkHour = userWithMetadata?.user?.agent_working_hours ?: "",
                userCity = _uiState.value.userCity.ifBlank {
                    userWithMetadata?.user?.agent_city ?: ""
                }
            )
            updateSaveButtonState()
        }
    }

    fun changeUserType() {
        viewModelScope.launch {
            changeUserTypeUseCase.invoke()
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            val updatedUser = mapUiStateToUpdatedUser()
            try {
                Log.d("updatedUser", "updateUser: $updatedUser")
                userUpdateUseCase.invoke(updatedUser)
            } catch (e: Exception) {
                Log.e("UpdateUser", "Failed to update user: ${e.message}")
            }
        }
    }

    private fun mapUiStateToUpdatedUser(): UpdatedUser {
        return UpdatedUser(
            agent_about = _uiState.value.userAbout,
            agent_city = _uiState.value.userCity,
            agent_full_address = _uiState.value.userFullAddress,
            agent_working_hours = _uiState.value.userWorkHour,
            lastname = _uiState.value.lastname,
            phone = _uiState.value.firstPhoneNumber,
            photo = _uiState.value.userImage
        )
    }

    private fun observeFullAddress() {
        viewModelScope.launch {
            getFullAddressUseCase.execute().collect { fullAddress ->
                _uiState.value = _uiState.value.copy(userCity = fullAddress)
            }
        }
    }

    private fun updateSaveButtonState() {
        _isSaveEnabled.value = !compareUiStateWithOriginalUser()
    }

    fun updateUserCity(value: String) {
        _uiState.value = _uiState.value.copy(userCity = value)
        updateSaveButtonState()
    }

    fun updateUserAbout(value: String) {
        _uiState.value = _uiState.value.copy(userAbout = value)
        updateSaveButtonState()
    }

    fun updateUserFullAddress(value: String) {
        _uiState.value = _uiState.value.copy(userFullAddress = value)
        updateSaveButtonState()
    }

    fun updateUserWorkHour(value: String) {
        _uiState.value = _uiState.value.copy(userWorkHour = value)
        updateSaveButtonState()
    }


    private fun compareUiStateWithOriginalUser(): Boolean {
        val user = originalUser ?: return true
        Log.d(
            "user",
            "compareUiStateWithOriginalUser: ${user.agent_about} ${user.agent_full_address} ${user.agent_working_hours} ${user.agent_city}"
        )
        Log.d(
            "user",
            "compareUiStateWithOriginalUser: ${_uiState.value.userAbout} ${_uiState.value.userCity} ${_uiState.value.userWorkHour}"
        )
        return _uiState.value.run {
            userAbout == user.agent_about &&
//                    firstname == user.firstname &&
                    userFullAddress == user.agent_full_address &&
                    userWorkHour == user.agent_working_hours &&
                    userCity == user.agent_city
//                    firstPhoneNumber == user.phone &&
//                    userImage == user.photo
        }
    }
}
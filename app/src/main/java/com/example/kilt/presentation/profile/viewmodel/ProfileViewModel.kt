package com.example.kilt.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.profile.usecase.CheckUserModerationStatusUseCase
import com.example.kilt.models.authentification.UserWithMetadata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val checkUserModerationStatusUseCase: CheckUserModerationStatusUseCase) :
    ViewModel() {


    private val _moderationStatus = mutableStateOf(false)
    var moderationStatus: State<Boolean> = _moderationStatus

    fun checkModerationStatus() {
        viewModelScope.launch {
            _moderationStatus.value = checkUserModerationStatusUseCase.execute().result
            Log.d("moderationStatus", "checkModerationStatus: ${_moderationStatus.value}")
        }

    }
}
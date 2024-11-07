package com.example.kilt.presentation.editprofile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.editprofile.usecase.GetUserPhoneNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase) :
    ViewModel() {

    val phoneNumbers = mutableStateOf<List<String>>(emptyList())

    fun loadPhoneNumbers(){
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

}
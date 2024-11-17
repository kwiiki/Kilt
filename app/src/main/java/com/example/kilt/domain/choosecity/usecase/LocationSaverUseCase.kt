package com.example.kilt.domain.choosecity.usecase

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationSaverUseCase @Inject constructor() {
    private val _selectedCity = MutableStateFlow<String?>(null)
    private val _selectedDistrict = MutableStateFlow<String?>(null)
    private val _selectedMicroDistrict = MutableStateFlow<String?>(null)

    val selectedCity: StateFlow<String?> = _selectedCity
    val selectedDistrict: StateFlow<String?> = _selectedDistrict
    val selectedMicroDistrict: StateFlow<String?> = _selectedMicroDistrict

    fun setSelectedCity(city: String) {
        _selectedCity.value = city
    }

    fun setSelectedDistrict(district: String) {
        Log.d("EditProfileViewModel", "setSelectedDistrict: $district")
        _selectedDistrict.value = district
    }

    fun setSelectedMicroDistrict(microDistrict: String) {
        _selectedMicroDistrict.value = microDistrict
    }
}
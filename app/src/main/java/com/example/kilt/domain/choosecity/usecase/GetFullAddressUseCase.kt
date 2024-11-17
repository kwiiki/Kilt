package com.example.kilt.domain.choosecity.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFullAddressUseCase @Inject constructor(
    private val locationSaverUseCase: LocationSaverUseCase
) {
    fun execute(): Flow<String> {
        return combine(
            locationSaverUseCase.selectedCity,
            locationSaverUseCase.selectedDistrict,
            locationSaverUseCase.selectedMicroDistrict
        ) { city, district, microDistrict ->
            Log.d("EditProfileViewModel", "execute1: $city")
            Log.d("EditProfileViewModel", "execute2: $city, $district")
            Log.d("EditProfileViewModel", "execute3: $city, $district, $microDistrict")

            // Фильтруем null и пустые строки
            val parts = listOf(city, district, microDistrict)
                .filter { !it.isNullOrEmpty() }

            // Формируем строку через запятую
            parts.joinToString(separator = ", ").also {
                Log.d("EditProfileViewModel", "Full address: $it")
            }
        }
    }
}


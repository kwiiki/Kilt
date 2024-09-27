package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.kato.District
import com.example.kilt.data.kato.MicroDistrict
import com.example.kilt.repository.KatoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChooseCityViewModel @Inject constructor(
    private val katoRepository: KatoRepository
) : ViewModel() {

    private val _selectedCity = mutableStateOf<String?>(null)
    val selectedCity: State<String?> get() = _selectedCity

    // Состояние для списка районов
    private val _districts = mutableStateOf<List<District>?>(null)
    val districts: State<List<District>?> get() = _districts

    private val _selectedDistrict = mutableStateOf<District?>(null)
    val selectedDistrict: State<District?> = _selectedDistrict

    private val _selectedMicroDistrict = mutableStateOf<MicroDistrict?>(null)
    val selectedMicroDistrict: State<MicroDistrict?> = _selectedMicroDistrict

    private val _microdistricts = mutableStateOf<List<MicroDistrict>?>(null)
    val microdistricts: State<List<MicroDistrict>?> get() = _microdistricts

    private val _currentScreen = mutableStateOf(0)
    val currentScreen: State<Int> = _currentScreen

    //    val districts = MutableLiveData<List<District>>()
    private val _expandedDistricts = mutableStateListOf<District>()
    val expandedDistricts: SnapshotStateList<District> get() = _expandedDistricts
    // Функция для выбора города с соответствующими ID
    fun selectCity(city: String) {
        _selectedCity.value = city
        _currentScreen.value = 1  // Переход на экран районов
        when (city) {
            "г.Алматы" -> getDistrictsByCityId("750000000")
            "г.Астана" -> getDistrictsByCityId("710000000")
            "г.Шымкент" -> getDistrictsByCityId("790000000")
            "Алматинская область" -> getDistrictsByCityId("190000000")
            else -> _districts.value = null
        }
    }
    private fun getDistrictsByCityId(cityId: String) {
        viewModelScope.launch {
            try {
                val response = katoRepository.getKatoById(cityId)
                _districts.value = response.list.map { kato ->
                    District(
                        id = kato.id,
                        parentId = kato.parent_id,
                        name = kato.name
                    )
                }
            } catch (e: Exception) {
                // Обработка ошибки
                _districts.value = emptyList()
            }
        }
    }

    fun toggleDistrictExpansion(district: District) {
        if (_expandedDistricts.contains(district)) {
            _expandedDistricts.remove(district)
        } else {
            _expandedDistricts.add(district)
        }
    }
    fun isDistrictSelected(district: District): Boolean {
        return _selectedDistrict.value == district
    }
    fun selectDistrict(district: District) {
        _selectedDistrict.value = district
        _currentScreen.value = 2  // Переход на экран микрорайонов
        viewModelScope.launch {
            val response = katoRepository.getMicroDistrict(district.id)
            _microdistricts.value = response.list
        }
    }
    fun goBack() {
        if (_currentScreen.value > 0) {
            _currentScreen.value = _currentScreen.value - 1
        }
    }
    fun selectMicroDistrict(microDistrict: MicroDistrict) {
        _selectedMicroDistrict.value = microDistrict
        viewModelScope.launch {
            val microdistricts = katoRepository.getMicroDistrict(microDistrict.id)
            _microdistricts.value = microdistricts.list
        }
    }
    // Функция для сброса выбора города и списка районов
    fun resetSelection() {
        _currentScreen.value = 0
    }
}

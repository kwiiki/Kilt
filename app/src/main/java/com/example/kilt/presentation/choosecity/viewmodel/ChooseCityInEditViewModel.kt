package com.example.kilt.presentation.choosecity.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.singelton.LocationManager
import com.example.kilt.domain.choosecity.modul.MicroDistrict
import com.example.kilt.domain.choosecity.usecase.GetKatoByIdUseCase
import com.example.kilt.domain.choosecity.usecase.GetMicroDistrictByIdUseCase
import com.example.kilt.models.kato.District
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseCityInEditViewModel @Inject constructor(
    private val getKatoByIdUseCase: GetKatoByIdUseCase,
    private val getMicroDistrictByIdUseCase: GetMicroDistrictByIdUseCase
) : ViewModel() {

    private val _microDistrictsByDistrict = mutableStateMapOf<String, List<MicroDistrict>>()
    val microDistrictsByDistrict: Map<String, List<MicroDistrict>> = _microDistrictsByDistrict

    private val _selectedCity = mutableStateOf<String?>(null)
    val selectedCity: State<String?> get() = _selectedCity

    private val _loadingDistricts = mutableStateMapOf<String, Boolean>()
    val loadingDistricts: Map<String, Boolean> = _loadingDistricts

    private val _selectedMicroDistrict = mutableStateOf<MicroDistrict?>(null)
    val selectedMicroDistrict: State<MicroDistrict?> = _selectedMicroDistrict

    private val _currentScreen = mutableStateOf(0)
    val currentScreen: State<Int> = _currentScreen

    private val _districts = mutableStateOf<List<District>?>(null)
    val districts: State<List<District>?> get() = _districts

    private val _selectAllInDistrict = mutableStateMapOf<String, Boolean>()
    val selectAllInDistrict: Map<String, Boolean> get() = _selectAllInDistrict

    private val _selectAllInCity = mutableStateMapOf<String, Boolean>()
    val selectAllInCity: Map<String, Boolean> get() = _selectAllInCity

    val loadingDistrict = mutableStateOf(false)

    private val _selectedAllDistrictId = mutableStateOf<String?>(null)
    val selectedAllDistrictId:State<String?> = _selectedAllDistrictId

    private val _selectedDistrict = mutableStateListOf<String>()
    val selectedDistrict = _selectedDistrict

    private val _tempCity = mutableStateOf<String?>(null)
    private val _tempDistricts = mutableStateListOf<String>()
    private val _tempMicroDistrict = mutableStateOf<MicroDistrict?>(null)

    //* Это нужно чтобы следить за состоянием выбранного микрорайона для того чтобы другие микрорайоны сделать enabled
    fun selectMicroDistrict(microDistrict: MicroDistrict) {
        val currentSelection = _selectedMicroDistrict.value
        // Если микрорайон уже выбран, удаляем его
        if (currentSelection == microDistrict) {
            _selectedMicroDistrict.value = null
            LocationManager.setSelectedMicroDistrict("")  // Убираем выбранный микрорайон из LocationManager
        } else {
            // Если микрорайон не выбран, выбираем его
            _selectedMicroDistrict.value = microDistrict
            LocationManager.setSelectedMicroDistrict(microDistrict.name)  // Добавляем микрорайон в LocationManager
        }
        Log.d("selectedMicroDistricts", "changeValue: ${_selectedMicroDistrict.value?.name}")
    }
    // * это мне нужно когда кликаю на checkbox стали enabled все микрорайоны определенного района
    fun toggleSelectAllInDistrict(districtId: String, name: String) {
        Log.d("district", "toggleSelectAllInDistrict: $name")
        val isSelectingAll = !(_selectAllInDistrict[districtId] ?: false)
        _selectAllInDistrict[districtId] = isSelectingAll

        // Включаем или выключаем все микрорайоны для других районов в зависимости от состояния
        if (isSelectingAll) {
            LocationManager.setSelectedDistrict(name)  // Добавляем выбранный район в LocationManager

            // Блокируем выбор микрорайонов для других районов
            _selectAllInDistrict.keys.forEach { id ->
                if (id != districtId) {
                    _selectAllInDistrict[id] = false
                }
            }
        } else {
            selectedDistrict.remove(name)
            LocationManager.setSelectedDistrict("")  // Убираем выбранный район из LocationManager
        }
    }
    fun toggleSelectAllInCity(selectedCity: String) {
        val isSelected = _selectAllInCity[selectedCity] ?: false
        _selectAllInCity[selectedCity] = !isSelected
        Log.d("city", "toggleSelectAllInCity: $selectedCity")

        if (isSelected) {
            LocationManager.setSelectedCity("")  // Clear city if it's being de-selected
        } else {
            LocationManager.setSelectedCity(selectedCity)  // Set city if it's being selected
        }

        _selectAllInCity.keys.forEach { city ->
            _selectAllInCity[city] = city == selectedCity && !isSelected
        }

        // Reset districts and microdistricts if city is deselected
        if (!isSelected) {
            _selectAllInDistrict.keys.forEach { districtId ->
                _selectAllInDistrict[districtId] = false
            }
            _selectedMicroDistrict.value = null  // Clear selected microdistrict
        }
    }

    fun isAnyCitySelected(): Boolean {
        return _selectAllInCity.values.any { it }
    }

    // Функция для проверки состояния микрорайонов других районов
    fun isDistrictSelected(districtId: String): Boolean {
        return _selectAllInDistrict[districtId] == true
    }

    fun loadMicroDistricts(districtId: String) {
        _loadingDistricts[districtId] = true
        viewModelScope.launch {
            try {
                val microDistricts = getMicroDistrictByIdUseCase.execute(districtId)
                _microDistrictsByDistrict[districtId] = microDistricts.sortedBy { it.name }
            } catch (e: Exception) {
            } finally {
                _loadingDistricts[districtId] = false
            }
        }
    }

    fun selectCity(city: String) {
        _selectedCity.value = city
        _currentScreen.value = 1
        when (city) {
            "г.Алматы" -> getDistrictsByCityId("750000000")
            "г.Астана" -> getDistrictsByCityId("710000000")
            "г.Шымкент" -> getDistrictsByCityId("790000000")
            "Алматинская область" -> getDistrictsByCityId("190000000")
        }
    }
    private fun getDistrictsByCityId(cityId: String) {
        loadingDistrict.value = true
        viewModelScope.launch {
            try {
                val response = getKatoByIdUseCase.execute(cityId)
                _districts.value = response.list.map { kato ->
                    District(
                        id = kato.id,
                        parentId = kato.parent_id,
                        name = kato.name
                    )
                }.sortedBy { it.name }
            } catch (e: Exception) {
            } finally {
                loadingDistrict.value = false
            }
        }
    }
    fun goBack() {
        if (_currentScreen.value > 0) {
            _currentScreen.value -= 1
        }
        if (_currentScreen.value == 0) {
            _selectedCity.value = null
        }
    }

    fun selectCites(city: String) {
        _tempCity.value = city
    }

    // Temporary select district
    fun selectDistrict(district: String) {
        if (!_tempDistricts.contains(district)) {
            _tempDistricts.add(district)
        } else {
            _tempDistricts.remove(district)
        }
    }

    // Temporary select microdistrict
    fun selectMicroDistricts(microDistrict: MicroDistrict) {
        _tempMicroDistrict.value = microDistrict
    }

    // Apply selected city, district, and microdistrict
    fun applySelection() {
        _selectedCity.value = _tempCity.value
        _selectedDistrict.clear()
        _selectedDistrict.addAll(_tempDistricts)
        _selectedMicroDistrict.value = _tempMicroDistrict.value

        // Optional: save selections to location manager
        LocationManager.setSelectedCity(_selectedCity.value ?: "")
        LocationManager.setSelectedDistrict(_selectedDistrict.joinToString(","))
        LocationManager.setSelectedMicroDistrict(_selectedMicroDistrict.value?.name ?: "")
    }
    fun resetSelection() {
        _currentScreen.value = 0
        _selectedCity.value = null
        _selectAllInDistrict.clear()
        _selectAllInCity.clear()
        _tempCity.value = null
        _tempDistricts.clear()
        _tempMicroDistrict.value = null
    }
}
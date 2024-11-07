package com.example.kilt.presentation.choosecity.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val loadingDistrict = mutableStateOf(false)

    private val _selectedAllDistrictId = mutableStateOf<String?>(null)
    val selectedAllDistrictId:State<String?> = _selectedAllDistrictId

    //* Это нужно чтобы следить за состоянием выбранного микрорайона для того чтобы другие микрорайоны сделать enabled
    fun selectMicroDistrict(microDistrict: MicroDistrict) {
        _selectedMicroDistrict.value = if (_selectedMicroDistrict.value == microDistrict) null else microDistrict
        Log.d("selectedMicroDistricts", "changeValue: ${_selectedMicroDistrict.value?.name}")

    }
    // * это мне нужно когда кликаю на checkbox стали enabled все микрорайоны определенного района
    fun toggleSelectAllInDistrict(districtId: String) {
        val isSelectingAll = !(_selectAllInDistrict[districtId] ?: false)
        _selectAllInDistrict[districtId] = isSelectingAll

        // Включаем или выключаем все микрорайоны для других районов в зависимости от состояния
        if (isSelectingAll) {
            // Для других районов блокируем выбор микрорайонов
            _selectAllInDistrict.keys.forEach { id ->
                if (id != districtId) {
                    _selectAllInDistrict[id] = false
                }
            }
        }
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
    fun resetSelection() {
        _currentScreen.value = 0
        _selectedCity.value = null
        _selectAllInDistrict.clear()
    }
}
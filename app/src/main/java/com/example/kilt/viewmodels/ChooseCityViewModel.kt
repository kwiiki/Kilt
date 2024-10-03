package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.kato.District
import com.example.kilt.data.kato.MicroDistrict
import com.example.kilt.data.kato.ResidentialComplex
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

    private val _districts = mutableStateOf<List<District>?>(null)
    val districts: State<List<District>?> get() = _districts

    private val _selectedDistrict = mutableStateOf<District?>(null)
    val selectedDistrict: State<District?> = _selectedDistrict

    private val _selectedMicroDistrict = mutableStateOf<MicroDistrict?>(null)
    val selectedMicroDistrict: State<MicroDistrict?> = _selectedMicroDistrict

    private val _selectedMicroDistricts = mutableStateListOf<MicroDistrict>()
    val selectedMicroDistricts: List<MicroDistrict> = _selectedMicroDistricts

    private val _microDistricts = mutableStateOf<List<MicroDistrict>?>(null)
    val microDistricts: State<List<MicroDistrict>?> get() = _microDistricts

    private val _residentialComplexes = mutableStateOf<List<ResidentialComplex>>(emptyList())
    val residentialComplexes: State<List<ResidentialComplex>> = _residentialComplexes

    private val _currentScreen = mutableStateOf(0)
    val currentScreen: State<Int> = _currentScreen

    private val _expandedDistricts = mutableStateListOf<District>()
    val expandedDistricts: SnapshotStateList<District> get() = _expandedDistricts

    private val _isRentSelected = mutableStateOf(true)
    val isRentSelected: State<Boolean> get() = _isRentSelected

    private val _isBuySelected = mutableStateOf(false)
    val isBuySelected: State<Boolean> get() = _isBuySelected

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _katoPathList = mutableStateListOf<String>()
    val katoPathList: List<String> = _katoPathList

    private val _isExpanded = mutableStateOf<Boolean>(false)
    val isExpanded:State<Boolean> = _isExpanded

    val selectCity = mutableStateOf<String?>(null)

    private val _microDistrictsByDistrict = mutableStateMapOf<String, List<MicroDistrict>>()
    val microDistrictsByDistrict: Map<String, List<MicroDistrict>> = _microDistrictsByDistrict

    private val _selectedComplexNames = mutableStateListOf<String>()
    val selectedComplexNames: List<String> = _selectedComplexNames

    private val _selectedComplexIds = mutableStateListOf<Int>()
    val selectedComplexIds: List<Int> = _selectedComplexIds

    fun selectCity(city: String) {
        _selectedCity.value = city
        _currentScreen.value = 1   // Navigate to the district screen
        when (city) {
            "г.Алматы" -> getDistrictsByCityId("750000000")
            "г.Астана" -> getDistrictsByCityId("710000000")
            "г.Шымкент" -> getDistrictsByCityId("790000000")
            "Алматинская область" -> getDistrictsByCityId("190000000")
            else -> _districts.value = null
        }
    }
    fun addComplexId(id: Int) {
        if (!_selectedComplexIds.contains(id)) {
            _selectedComplexIds.add(id)
        }
    }

    fun removeComplexId(id: Int) {
        _selectedComplexIds.remove(id)
    }

    fun isComplexSelected(complexId: Int): Boolean {
        return selectedComplexIds.contains(complexId)
    }

    fun addComplexName(name:String) {
        if (!_selectedComplexNames.contains(name)) {
            _selectedComplexNames.add(name)
        }
    }

    fun removeComplexName(name: String) {
        _selectedComplexNames.remove(name)
    }

    fun addOrRemoveKatoPath(katoPath: String, isChecked: Boolean):List<String> {
        if (isChecked) {
            if (!_katoPathList.contains(katoPath)) {
                _katoPathList.add(katoPath)
            }
        } else {
            _katoPathList.remove(katoPath)
        }
        return katoPathList
    }
    private fun loadResidentialComplexes(city: String) {
        viewModelScope.launch {
            try {
                val cityName = city.replace("г.", "").replace("\\s+".toRegex(), " ").trim()
                val response = katoRepository.getResidentialComplex(cityName)
                _residentialComplexes.value = response.list.sortedBy { it.residential_complex_name }
            } catch (e: Exception) {
                Log.e("residentialList", "Error loading residential complexes", e)
                _residentialComplexes.value = emptyList()
            }
        }
    }
    fun toggleRentBuySelection(isRent: Boolean) {
        _isRentSelected.value = isRent
        _isBuySelected.value = !isRent
        if (_selectedCity.value != null && _isBuySelected.value) {
            loadResidentialComplexes(_selectedCity.value!!)
        }
    }
    fun toggleMicroDistrictSelection(microDistrict: MicroDistrict, isSelected: Boolean) {
        Log.d("selectedMicro", "toggleMicroDistrictSelection: ${_selectedMicroDistricts.size}")
        if (isSelected) {
            if (!_selectedMicroDistricts.contains(microDistrict)) {
                _selectedMicroDistricts.add(microDistrict)
            }
        } else {
            _selectedMicroDistricts.remove(microDistrict)
        }
    }
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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
                }.sortedBy { it.name }
            } catch (e: Exception) {
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
    fun selectDistrict(district: District) {
//        _selectedDistrict.value = district
        viewModelScope.launch {
            try {
                val response = katoRepository.getMicroDistrict(district.id)
                _microDistrictsByDistrict[district.id] = response.list.sortedBy { it.name }
            } catch (e: Exception) {
            }
        }
    }
    fun goBack() {
        if (_currentScreen.value > 0) {
            _currentScreen.value -= 1
        }
        if(_currentScreen.value == 0){
            _selectedCity.value = null
        }
    }
    fun resetSelection() {
        _currentScreen.value = 0
        _isBuySelected.value = false
        _isRentSelected.value = true
        _selectedCity.value = null
        _katoPathList.clear()
        _selectedMicroDistricts.clear()
        _expandedDistricts.clear()
        _selectedDistrict.value = null
        _selectedComplexIds.clear()
        Log.d("resetSelection", "resetSelection: ${_katoPathList.size}")
    }
}

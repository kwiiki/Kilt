package com.example.kilt.presentation.config.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.models.config.FilterItem
import com.example.kilt.domain.config.repository.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class ConfigViewModel @Inject constructor(private val configRepository: ConfigRepository) : ViewModel() {

    val config = configRepository.config

    private val _dealType = MutableStateFlow(1)
    val dealType: StateFlow<Int> = _dealType
    private val _listingType = MutableStateFlow(1)
    val listingType: StateFlow<Int> = _listingType
    private val _propertyType = MutableStateFlow(1)
    val propertyType: StateFlow<Int> = _propertyType

    private val _listingProps = MutableStateFlow<List<String>?>(null)
    val listingProps: StateFlow<List<String>?> = _listingProps

    private val _listingTop = MutableStateFlow<List<String>?>(null)
    val listingTop: StateFlow<List<String>?> = _listingTop

    private val _listingInfo = mutableStateOf<List<String>>(emptyList())
    val listingInfo: State<List<String>> = _listingInfo

    private val propMapping: Map<String, () -> Flow<List<FilterItem>>> = mapOf(
        "floor" to { flowOf(config.value?.propMapping?.floor?.list ?: emptyList()) },
        "num_rooms" to { flowOf(config.value?.propMapping?.num_rooms?.list ?: emptyList()) },
        "furniture_list" to { flowOf(config.value?.propMapping?.furniture_list?.list ?: emptyList()) },
        "new_conveniences" to { flowOf(config.value?.propMapping?.new_conveniences?.list ?: emptyList()) },
        "rent_period" to { flowOf(config.value?.propMapping?.rent_period?.list ?: emptyList()) },
        "bathroom_inside" to { flowOf(config.value?.propMapping?.bathroom_inside?.list ?: emptyList()) },
        "security" to { flowOf(config.value?.propMapping?.security?.list ?: emptyList()) },
        "new_balcony" to { flowOf(config.value?.propMapping?.new_balcony?.list ?: emptyList()) },
        "toilet_separation" to { flowOf(config.value?.propMapping?.toilet_separation?.list ?: emptyList()) },
        "loggia" to { flowOf(config.value?.propMapping?.loggia?.list ?: emptyList()) },
        "windows" to { flowOf(config.value?.propMapping?.windows?.list ?: emptyList()) },
        "suits_for" to { flowOf(config.value?.propMapping?.suits_for?.list ?: emptyList()) },
        "construction_type" to { flowOf(config.value?.propMapping?.construction_type?.list ?: emptyList()) },
        "furniture" to { flowOf(config.value?.propMapping?.furniture?.list ?: emptyList()) },
        "former_dormitory" to { flowOf(config.value?.propMapping?.former_dormitory?.list ?: emptyList()) },
        "internet" to { flowOf(config.value?.propMapping?.internet?.list ?: emptyList()) },
        "balcony_glass" to { flowOf(config.value?.propMapping?.balcony_glass?.list ?: emptyList()) },
        "door" to { flowOf(config.value?.propMapping?.door?.list ?: emptyList()) },
        "parking" to { flowOf(config.value?.propMapping?.parking?.list ?: emptyList()) },
        "floor_material" to { flowOf(config.value?.propMapping?.floor_material?.list ?: emptyList()) },
        "is_bailed" to { flowOf(config.value?.propMapping?.is_bailed?.list ?: emptyList()) },
        "bathroom" to { flowOf(config.value?.propMapping?.bathroom?.list ?: emptyList()) },
        "balcony" to { flowOf(config.value?.propMapping?.balcony?.list ?: emptyList()) },
        "condition" to { flowOf(config.value?.propMapping?.condition?.list ?: emptyList()) },
        "heating" to { flowOf(config.value?.propMapping?.heating?.list ?: emptyList()) },
        "sewer" to { flowOf(config.value?.propMapping?.sewer?.list ?: emptyList()) },
        "drinking_water" to { flowOf(config.value?.propMapping?.drinking_water?.list ?: emptyList()) },
        "irrigation_water" to { flowOf(config.value?.propMapping?.irrigation_water?.list ?: emptyList()) },
        "electricity" to { flowOf(config.value?.propMapping?.electricity?.list ?: emptyList()) },
        "telephone" to { flowOf(config.value?.propMapping?.telephone?.list ?: emptyList()) },
        "designation" to { flowOf(config.value?.propMapping?.designation?.list ?: emptyList()) },
        "where_located" to { flowOf(config.value?.propMapping?.where_located?.list ?: emptyList()) },
        "active_business" to { flowOf(config.value?.propMapping?.active_business?.list ?: emptyList()) },
        "has_renters" to { flowOf(config.value?.propMapping?.has_renters?.list ?: emptyList()) },
        "free_planning" to { flowOf(config.value?.propMapping?.free_planning?.list ?: emptyList()) },
        "business_condition" to { flowOf(config.value?.propMapping?.business_condition?.list ?: emptyList()) },
        "business_parking" to { flowOf(config.value?.propMapping?.business_parking?.list ?: emptyList()) },
        "business_entrance" to { flowOf(config.value?.propMapping?.business_entrance?.list ?: emptyList()) },
        "communications" to { flowOf(config.value?.propMapping?.communications?.list ?: emptyList()) },
        "line_of_houses" to { flowOf(config.value?.propMapping?.line_of_houses?.list ?: emptyList()) }
    )
    init {
        viewModelScope.launch {
            Log.d("ConfigDownload", "ConfigViewModel : Load in configViewModel ")
            configRepository.loadConfig()
            loadDataWithCurrentTypes()
        }
    }

    private fun loadDataWithCurrentTypes() {
        loadListingProps(_dealType.value, _listingType.value, _propertyType.value)
        loadListingTop(_dealType.value, _listingType.value, _propertyType.value)
        loadListingInfo(_dealType.value,_listingType.value,_propertyType.value)
    }

    fun setTypes(dealType: Int, listingType: Int, propertyType: Int) {
        Log.d("ConfigViewModel", "Set Types: dealType=$dealType, listingType=$listingType, propertyType=$propertyType")
        _dealType.value = dealType
        _listingType.value = listingType
        _propertyType.value = propertyType
        loadDataWithCurrentTypes()
    }

    private fun loadListingInfo(dealType: Int, listingType: Int, propertyType: Int) {
        viewModelScope.launch {
            val result = configRepository.getListingInfo(dealType, listingType, propertyType)
            _listingInfo.value = result ?: emptyList()
        }
    }
    private fun loadListingProps(dealType: Int, listingType: Int, propertyType: Int) {
        viewModelScope.launch {
            Log.d("ConfigViewModel", "Loading props for dealType=$dealType, listingType=$listingType, propertyType=$propertyType")
            val result = configRepository.getListingProps(dealType, listingType, propertyType)
            _listingProps.value = result ?: emptyList()
            Log.d("ConfigViewModel", "Loaded props: ${_listingProps.value}")
        }
    }


    private fun loadListingTop(dealType: Int = 1, listingType: Int = 1, propertyType: Int = 1) {
        viewModelScope.launch {
            _listingTop.value = configRepository.getListingTops(dealType, listingType, propertyType)
        }
    }

    fun loadHomeSale() {
        loadListingProps(_dealType.value, _listingType.value, _propertyType.value)
    }

    fun getFilterOptions(prop: String): Flow<List<FilterItem>> {
        return propMapping[prop]?.invoke() ?: flowOf(emptyList())
    }

//    fun getFilterOptions(prop: String): List<Any>? {
//        return when (prop) {
//            "floor" -> config.value?.propMapping?.floor?.list
//            "num_rooms" -> config.value?.propMapping?.num_rooms?.list
//            "furniture_list" -> config.value?.propMapping?.furniture_list?.list
//            "new_conveniences" -> config.value?.propMapping?.new_conveniences?.list
//            "rent_period" -> config.value?.propMapping?.rent_period?.list
//            "bathroom_inside" -> config.value?.propMapping?.bathroom_inside?.list
//            "security" -> config.value?.propMapping?.security?.list
//            "new_balcony" -> config.value?.propMapping?.new_balcony?.list
//            "toilet_separation" -> config.value?.propMapping?.toilet_separation?.list
//            "loggia" -> config.value?.propMapping?.loggia?.list
//            "windows" -> config.value?.propMapping?.windows?.list
//            "suits_for" -> config.value?.propMapping?.suits_for?.list
//            "construction_type" -> config.value?.propMapping?.construction_type?.list
//            "furniture" -> config.value?.propMapping?.furniture?.list
//            "former_dormitory" -> config.value?.propMapping?.former_dormitory?.list
//            "internet" -> config.value?.propMapping?.internet?.list
//            "balcony_glass" -> config.value?.propMapping?.balcony_glass?.list
//            "door" -> config.value?.propMapping?.door?.list
//            "parking" -> config.value?.propMapping?.parking?.list
//            "floor_material" -> config.value?.propMapping?.floor_material?.list
//            "is_bailed" -> config.value?.propMapping?.is_bailed?.list
//            "bathroom" -> config.value?.propMapping?.bathroom?.list
//            "balcony" -> config.value?.propMapping?.balcony?.list
//            "condition" -> config.value?.propMapping?.condition?.list
//            "heating" -> config.value?.propMapping?.heating?.list
//            "sewer" -> config.value?.propMapping?.sewer?.list
//            "drinking_water" -> config.value?.propMapping?.drinking_water?.list
//            "irrigation_water" -> config.value?.propMapping?.irrigation_water?.list
//            "electricity" -> config.value?.propMapping?.electricity?.list
//            "telephone" -> config.value?.propMapping?.telephone?.list
//            "designation" -> config.value?.propMapping?.designation?.list
//            "where_located" -> config.value?.propMapping?.where_located?.list
//            "active_business" -> config.value?.propMapping?.active_business?.list
//            "has_renters" -> config.value?.propMapping?.has_renters?.list
//            "free_planning" -> config.value?.propMapping?.free_planning?.list
//            "business_condition" -> config.value?.propMapping?.business_condition?.list
//            "business_parking" -> config.value?.propMapping?.business_parking?.list
//            "business_entrance" -> config.value?.propMapping?.business_entrance?.list
//            "communications" -> config.value?.propMapping?.communications?.list
//            "line_of_houses" -> config.value?.propMapping?.line_of_houses?.list
//            else -> null
//        }
//    }
}

fun getId(item: FilterItem): String = item.id.toString()
fun getName(item: FilterItem): String = item.name

//fun <T> getId(item: T): String {
//    return when (item) {
//        is NumOfFloor -> item.id.toString()
//        is com.example.kilt.data.config.NumRoom -> item.id.toString()
//        is com.example.kilt.data.config.ListOfFurniture -> item.id.toString()
//        is Conveniences -> item.id.toString()
//        is RentPeriodItem -> item.id.toString()
//        is com.example.kilt.data.config.BathroomInsideItem -> item.id.toString()
//        is com.example.kilt.data.config.SecurityItem -> item.id.toString()
//        is NewBalconyItem -> item.id.toString()
//        is ToiletSeparationItem -> item.id.toString()
//        is LoggiaItem ->item.id.toString()
//        is WindowsItem -> item.id.toString()
//        is SuitsForItem -> item.id.toString()
//        is TypesOfConstruction -> item.id.toString()
//        is Furnituries -> item.id.toString()
//        is com.example.kilt.data.config.FormerDormitoryItem -> item.id.toString()
//        is com.example.kilt.data.config.InternetItem -> item.id.toString()
//        is BalconyGlassItem -> item.id.toString()
//        is com.example.kilt.data.config.DoorItem -> item.id.toString()
//        is ParkingItem -> item.id.toString()
//        is FloorMaterialItem -> item.id.toString()
//        is com.example.kilt.data.config.IsBailedItem -> item.id.toString()
//        is BathroomItem ->item.id.toString()
//        is BalconyItem ->item.id.toString()
//        is com.example.kilt.data.config.Condition ->item.id.toString()
//        is com.example.kilt.data.config.Heating ->item.id.toString()
//        is com.example.kilt.data.config.Sewer ->item.id.toString()
//        is com.example.kilt.data.config.DrinkingWater ->item.id.toString()
//        is com.example.kilt.data.config.IrrigationWater ->item.id.toString()
//        is com.example.kilt.data.config.Electricity ->item.id.toString()
//        is Telephone ->item.id.toString()
//        is Designations ->item.id.toString()
//        is Locateds ->item.id.toString()
//        is com.example.kilt.data.config.ActiveBusiness ->item.id.toString()
//        is com.example.kilt.data.config.FreePlanning ->item.id.toString()
//        is com.example.kilt.data.config.BusinessCondition ->item.id.toString()
//        is com.example.kilt.data.config.BusinessParking ->item.id.toString()
//        is com.example.kilt.data.config.BusinessEntrance ->item.id.toString()
//        is com.example.kilt.data.config.Communications ->item.id.toString()
//        is Houselines -> item.id.toString()
//        is com.example.kilt.data.config.HasRenters ->item.id.toString()
//        else -> item.toString()
//    }
//}

//fun <T> getName(item: T): String {
//    return when (item) {
//        is NumOfFloor -> item.name
//        is com.example.kilt.data.config.NumRoom -> item.name
//        is com.example.kilt.data.config.ListOfFurniture -> item.name
//        is Conveniences -> item.name
//        is RentPeriodItem -> item.name
//        is com.example.kilt.data.config.BathroomInsideItem -> item.name
//        is com.example.kilt.data.config.SecurityItem -> item.name
//        is NewBalconyItem -> item.name
//        is ToiletSeparationItem -> item.name
//        is LoggiaItem ->item.name
//        is WindowsItem -> item.name
//        is SuitsForItem -> item.name
//        is TypesOfConstruction -> item.name
//        is Furnituries -> item.name
//        is com.example.kilt.data.config.FormerDormitoryItem -> item.name
//        is com.example.kilt.data.config.InternetItem -> item.name
//        is BalconyGlassItem -> item.name
//        is com.example.kilt.data.config.DoorItem -> item.name
//        is ParkingItem -> item.name
//        is FloorMaterialItem -> item.name
//        is com.example.kilt.data.config.IsBailedItem -> item.name
//        is BathroomItem ->item.name
//        is BalconyItem ->item.name
//        is com.example.kilt.data.config.Condition ->item.name
//        is com.example.kilt.data.config.Heating ->item.name
//        is com.example.kilt.data.config.Sewer ->item.name
//        is com.example.kilt.data.config.DrinkingWater ->item.name
//        is com.example.kilt.data.config.IrrigationWater ->item.name
//        is com.example.kilt.data.config.Electricity ->item.name
//        is Telephone ->item.name
//        is Designations ->item.name
//        is Locateds ->item.name
//        is com.example.kilt.data.config.ActiveBusiness ->item.name
//        is com.example.kilt.data.config.FreePlanning ->item.name
//        is com.example.kilt.data.config.BusinessCondition ->item.name
//        is com.example.kilt.data.config.BusinessParking ->item.name
//        is com.example.kilt.data.config.BusinessEntrance ->item.name
//        is com.example.kilt.data.config.Communications ->item.name
//        is Houselines -> item.name
//        is com.example.kilt.data.config.HasRenters ->item.name
//        else -> item.toString()
//    }
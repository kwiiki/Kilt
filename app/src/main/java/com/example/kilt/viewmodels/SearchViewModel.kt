package com.example.kilt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.Area
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.TConfig
import com.example.kilt.data.THomeSale
import com.example.kilt.data.config.Price
import com.example.kilt.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _searchResult = MutableStateFlow<SearchResponse?>(null)
    val searchResult: StateFlow<SearchResponse?> = _searchResult.asStateFlow()

    private val _searchResultCount = MutableStateFlow<String?>(null)
    val searchResultCount: StateFlow<String?> = _searchResultCount.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    private var hasLoadedData = false
    private val _filters = MutableStateFlow(
        Filters(
            deal_type = 1,
            property_type = 1,
            listing_type = 1,
            price = Price(from = "0", to = "500000"),
            num_rooms = listOf(),
            status = 1,
            floor = listOf(1),
            area = Area(from = "40", to = "80"),
//            rent_period = listOf(1),
//            furniture_list = listOf(),
//            new_conveniences = listOf(1,3,5,2),
//            toilet_separation = listOf(1,2)
        )
    )
    val filters: StateFlow<Filters> = _filters.asStateFlow()

    fun updateFilters(newFilters: Filters) {
        _filters.value = newFilters  // Даем новые фильтры нашему POST запросу в Body
        Log.d("SearchViewModel", "Filters updated: ${_filters.value}")
        performSearch()
        getCountBySearchResult()// Автоматом отправляем запрос при обновлении фильторм
    }


    fun updateRangeFilter(prop: String, min: String, max: String) {
        val currentFilters = _filters.value
        val updatedFilters = when (prop) {
            "price" -> currentFilters.copy(price = Price(from = min, to = max))
            "area" -> currentFilters.copy(area = Area(from = min, to = max))
            else -> currentFilters
        }
        updateFilters(updatedFilters)
    }

    fun updateListFilter(prop: String, selectedValues: List<Int>) {
        val currentFilters = _filters.value
        val updatedFilters = when (prop) {
            "num_rooms" -> currentFilters.copy(num_rooms = selectedValues)
            "floor" -> currentFilters.copy(floor = selectedValues)
            "rent_period" -> currentFilters.copy(rent_period = selectedValues)
//            "furniture_list" ->currentFilters.copy(furniture_list = selectedValues)
//            "new_conveniences" ->currentFilters.copy(new_conveniences = selectedValues)
//            "toilet_separation" ->currentFilters.copy(toilet_separation = selectedValues)


            // Добавьте другие list-фильтры по необходимости
            else -> currentFilters
        }
        updateFilters(updatedFilters)
    }

    fun getPropertyById(id: String): PropertyItem? {
        return _searchResult.value?.list?.find { it.id.toString() == id }
    }

    fun getCountBySearchResult() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            Log.d("filterValue", "getCountBySearchResult: ${_filters.value}")
            try {
                val request = THomeSale(
                    filters = _filters.value,
                    config = TConfig(num_rooms = "list"),
                    page = 1,
                    sorting = "new"
                )
                Log.d("request", "getCountBySearchResult: $request")
                val resultCount = searchRepository.getResultBySearchCount(request)
                Log.d("resultCount", "getCountBySearchResult: $resultCount")
                _searchResultCount.value = resultCount.count
                Log.d("resultCount", "getCountBySearchResult: ${resultCount.count}")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("SearchViewModel", "Search error", e)

            }
        }
    }

    fun performSearch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val request = THomeSale(
                    filters = _filters.value,
                    config = TConfig(
                        residential_complex = "residential-complex",
                        num_rooms = "list",
                        rent_period = "1",
                        price = "range",
                        built_year = "range",
                        construction_type = "list",
                        floor = listOf("2"),
                        num_floors = "range",
                        furniture = "list",
                        area = "range",
                        kitchen_area = "range",
                        is_bailed = "list",
                        former_dormitory = "list",
                        bathroom = "list",
                        internet = "list",
                        balcony = "list",
                        balcony_glass = "list",
                        door = "list",
                        parking = "list",
                        floor_material = "list",
                        security = "list",
                        status = "",
                        user_type = "list",
                        kato_path = "like",
                        lat = "range",
                        lng = "range"
                    ),
                    page = 1,
                    sorting = "new"
                )
                val response = searchRepository.performSearch(request)
                _searchResult.value = response.copy(list = response.list.toList())
                Log.d("SearchViewModel", "Search completed, results: ${response.list.size}")
                Log.d("SearchViewModel", "Applied filters: ${_filters.value}")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("SearchViewModel", "Search error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    // Метод для принудительного обновления данных
    fun refreshSearch() {
        hasLoadedData = false
        performSearch()
    }
}


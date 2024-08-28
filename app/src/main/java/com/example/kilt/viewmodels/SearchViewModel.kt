package com.example.kilt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.TConfig
import com.example.kilt.data.THomeSale
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



    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()




    private var hasLoadedData = false

    private val _filters = MutableStateFlow(
        Filters(
            deal_type = 1,
            property_type = 1,
            listing_type = 1
        )
    )
    val filters: StateFlow<Filters> = _filters.asStateFlow()

    fun updateFilters(newFilters: Filters) {
        _filters.value = newFilters
        Log.d("SearchViewModel", "Filters updated: ${_filters.value}")
    }

    fun getPropertyById(id: String): PropertyItem? {
        return _searchResult.value?.list?.find { it.id.toString() == id }
    }

    fun performSearch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                Log.d("SearchViewModel", "Performing search with filters: ${_filters.value}")
                val request = THomeSale(
                    filters = _filters.value,
                    config = TConfig(
                        residential_complex = "residential-complex",
                        num_rooms = "list",
                        price = "range",
                        built_year = "range",
                        construction_type = "list",
                        floor = "list",
                        description = "",
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

                // Создайте копию списка перед его назначением в _searchResult
                _searchResult.value = response.copy(list = response.list.toList())

                Log.d("SearchViewModel", "Search completed, results: ${response.list.size}")
                hasLoadedData = true
            } catch (e: Exception) {
                _error.value = e.message
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


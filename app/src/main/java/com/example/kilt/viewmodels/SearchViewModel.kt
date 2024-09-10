@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.kilt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _searchResult = MutableStateFlow<SearchResponse?>(null)
    val searchResult: StateFlow<SearchResponse?> = _searchResult.asStateFlow()

    private val _searchResultCount = MutableStateFlow<String?>("0")
    val searchResultCount: StateFlow<String?> = _searchResultCount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _emptyList = MutableStateFlow(true)
    val emptyList: StateFlow<Boolean> = _emptyList.asStateFlow()

    private val _filters = MutableStateFlow(
        Filters(
            deal_type = 1,
            listing_type = 1,
            property_type = 1
        )
    )
    val filters: StateFlow<Filters> = _filters.asStateFlow()
    val searchResults: Flow<PagingData<PropertyItem>> = filters.flatMapLatest { filters ->
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(searchRepository, filters) }
        ).flow.cachedIn(viewModelScope)
    }
    init {
        performSearch()
        Log.d("SearchViewModel", "Initializing SearchViewModel")
//        getCountBySearchResult()
    }
    fun updateFilters(newFilters: Filters, prop: String) {
        _filters.value = searchRepository.updateFilters(_filters.value, newFilters,prop)
        Log.d("SearchViewModel", "Filters updated: ${_filters.value}")
    }
    fun updateRangeFilter(prop: String, min: Int, max: Int) {
        _filters.value = searchRepository.updateRangeFilter(_filters.value, prop, min, max)
        updateFilters(_filters.value,prop)
    }
    fun updateListFilter(prop: String, selectedValues: List<Int>) {
        _filters.value = searchRepository.updateListFilter(_filters.value, prop, selectedValues)
        Log.d("prop", "updateListFilter1111:$prop ")
        updateFilters(_filters.value, prop)
    }
    fun getPropertyById(id: String): PropertyItem? {
        return searchRepository.getPropertyById(id, _searchResult.value)
    }
    //    fun getCountBySearchResult() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _error.value = null
//            try {
//                val filtersMap = _filters.value.toMapWithoutEmptyArrays()
//                val request = searchRepository.createSearchRequest(filtersMap, 0, "new")
//                val resultCount = searchRepository.getResultBySearchCount(request)
//                _searchResultCount.value = resultCount.count
//            } catch (e: Exception) {
//                _error.value = e.message
//                Log.e("SearchViewModel", "Search count error", e)
//            } finally {
//                _isLoading.value = false
//            }l
//        }
//    }
    fun performSearch() {
        viewModelScope.launch {
            if (_filters == null) Log.e("SearchViewModel", "_filters is null")

            _isLoading.value = true
            _error.value = null
            try {
                val request = searchRepository.createSearchRequest(_filters.value, 1, "new")
                Log.d("SearchViewModel", "Created search request: $request")
                val response = searchRepository.performSearch(request)
                Log.d("SearchViewModel", "Received search response: $response")
                _searchResult.value = response.copy(list = response.list.toList())
                Log.d("SearchViewModel", "Updated searchResult: ${_searchResult.value}")
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Search error", e)
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
                Log.d("SearchViewModel", "Finished performSearch")
            }
        }
    }
}


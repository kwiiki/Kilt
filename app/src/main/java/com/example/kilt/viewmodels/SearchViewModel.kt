package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kilt.data.FilterValue
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
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
    private var isDataLoaded = false

    private val _searchResult = MutableStateFlow<SearchResponse?>(null)
    val searchResult: StateFlow<SearchResponse?> = _searchResult.asStateFlow()

    private val _searchResultCount = MutableStateFlow<String?>("")
    val searchResultCount: StateFlow<String?> = _searchResultCount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    private val _error = MutableStateFlow<String?>(null)

    private val _filters = MutableStateFlow(Filters())
    val filters: StateFlow<Filters> = _filters

    private val _listState = MutableStateFlow(LazyListState())
    val listState: StateFlow<LazyListState> = _listState.asStateFlow()

    private val _searchResultsFlow = MutableStateFlow<PagingData<PropertyItem>>(PagingData.empty())
    val searchResultsFlow = _searchResultsFlow.asStateFlow()

    private fun resetListState() {
        _listState.value = LazyListState()
    }

    init {
        updateSingleFilter("deal_type", 1)
        updateSingleFilter("listing_type", 1)
        updateSingleFilter("property_type", 1)
        performSearch()
        getCountBySearchResult()
    }

    fun getRangeFilterValues(prop: String): Pair<Int, Int> {
        return when (val filterValue = _filters.value.filterMap[prop]) {
            is FilterValue.RangeValue -> Pair(filterValue.from, filterValue.to)
            else -> Pair(0, Int.MAX_VALUE)
        }
    }

    fun getSelectedFilters(prop: String): List<Int> {
        return when (val filterValue = _filters.value.filterMap[prop]) {
            is FilterValue.ListValue -> filterValue.values
            else -> emptyList()
        }
    }

    private fun updateFilters(newFilters: Filters, prop: String) {
        _filters.value = searchRepository.updateFilters(_filters.value, newFilters, prop)
        isDataLoaded = false
        resetListState()// Если фильтры изменились, необходимо перезагрузить данные
    }

    fun updateRangeFilter(prop: String, from: Int, to: Int) {
        val newFilters = Filters(mutableMapOf(prop to FilterValue.RangeValue(from, to)))
        updateFilters(newFilters, prop)
    }

    fun updateSingleFilter(prop: String, value: Int) {
        val newFilters = Filters(mutableMapOf(prop to FilterValue.SingleValue(value)))
        updateFilters(newFilters, prop)
    }

    fun updateListFilter(prop: String, selectedValues: List<Int>) {
        val newFilters = Filters(mutableMapOf(prop to FilterValue.ListValue(selectedValues)))
        updateFilters(newFilters, prop)
        getCountBySearchResult()
        Log.d("prop", "updateListFilter1111:$prop")
    }

    fun getPropertyById(id: String): PropertyItem? {
        return searchRepository.getPropertyById(id, _searchResult.value)
    }

    private fun getCountBySearchResult() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val dealType = (_filters.value.filterMap["deal_type"] as? FilterValue.SingleValue)?.value ?: 1
                val listingType = (_filters.value.filterMap["listing_type"] as? FilterValue.SingleValue)?.value ?: 1
                val propertyType = (_filters.value.filterMap["property_type"] as? FilterValue.SingleValue)?.value ?: 1

                val request = searchRepository.createSearchRequest(
                    filters = _filters.value,
                    dealType = dealType,
                    propertyType = propertyType,
                    listingType = listingType,
                    page = 0,
                    sorting = "new"
                )
                Log.d("SearchViewModel", "getCountBySearchResult: $request")
                val resultCount = searchRepository.getResultBySearchCount(request)
                Log.d("SearchViewModel", "getCountBySearchResult: $resultCount")
                _searchResultCount.value = resultCount.count
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("SearchViewModel", "Search count error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun performSearch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val dealType = (_filters.value.filterMap["deal_type"] as? FilterValue.SingleValue)?.value ?: 1
                val listingType = (_filters.value.filterMap["listing_type"] as? FilterValue.SingleValue)?.value ?: 1
                val propertyType = (_filters.value.filterMap["property_type"] as? FilterValue.SingleValue)?.value ?: 1

                val request = searchRepository.createSearchRequest(
                    filters = _filters.value,
                    dealType = dealType,
                    propertyType = propertyType,
                    listingType = listingType,
                    page = 0,
                    sorting = "new"
                )
                Log.d("SearchViewModel", "performSearch: $request")
                val response = searchRepository.performSearch(request)
                _searchResult.value = response.copy(list = response.list.toList())

                val pager = Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        SearchPagingSource(
                            searchRepository,
                            _filters.value,
                            dealType,
                            propertyType,
                            listingType
                        )
                    }
                )
                pager.flow.cachedIn(viewModelScope).collect { pagingData ->
                    _searchResultsFlow.value = pagingData
                }
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



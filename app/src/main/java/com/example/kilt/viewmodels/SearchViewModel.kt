package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.kilt.enums.TypeFilters


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _dealType = mutableStateOf(1)
    val dealType: State<Int> = _dealType

    private val _listingType = mutableStateOf(1)
    val listingType: State<Int> = _listingType

    private val _propertyType = mutableStateOf(1)
    val propertyType: State<Int> = _propertyType
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

    private val searchJob = Job()
    private val searchScope = CoroutineScope(Dispatchers.Main + searchJob)

    init {
        updateFiltersAndSearch()
        performSearch()
    }
    private fun updateFiltersAndSearch() {
        resetNonMainFilters() // Сбрасываем неосновные фильтры
        updateSingleFilter(TypeFilters.DEAL_TYPE.value, dealType.value)
        updateSingleFilter(TypeFilters.LISTING_TYPE.value, listingType.value)
        updateSingleFilter(TypeFilters.PROPERTY_TYPE.value, propertyType.value)
        getCountBySearchResult()
    }

    private fun debouncedSearch() {
        searchJob.cancel()
        searchScope.launch {
            delay(300) // Задержка в 300 мс для того чтобы кнопки успевали загореть нужным цветом
            getCountBySearchResult()
        }
    }
    private fun resetNonMainFilters() {
        val newFilters = Filters()
        // Save only main filters
        _filters.value.filterMap[TypeFilters.DEAL_TYPE.value]?.let {
            newFilters.filterMap[TypeFilters.DEAL_TYPE.value] = it
        }
        _filters.value.filterMap[TypeFilters.LISTING_TYPE.value]?.let {
            newFilters.filterMap[TypeFilters.LISTING_TYPE.value] = it
        }
        _filters.value.filterMap[TypeFilters.PROPERTY_TYPE.value]?.let {
            newFilters.filterMap[TypeFilters.PROPERTY_TYPE.value] = it
        }
        _filters.value = newFilters
    }

    fun selectRent() {
        val listing = _listingType.value
        val property = if (listing == 2) 6 else _propertyType.value
        updateFilters(1, listing, property)
    }

    fun selectBuy() {
        val listing = _listingType.value
        val property = if (listing == 2) 6 else _propertyType.value
        updateFilters(2, listing, property)
    }

    fun selectResidential() {
        updateFilters(_dealType.value, 1, 1)
    }

    fun selectCommercial() {
        updateFilters(_dealType.value, 2, 6)
    }

    fun selectPropertyType(type: Int) {
        if (_listingType.value == 1) {
            updateFilters(_dealType.value, 1, type)
        }
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
    fun clearAllFilters() {
        _filters.value = Filters()
        updateFiltersAndSearch()
    }

    private fun updateFilters(newFilters: Filters, prop: String) {
        _filters.value = searchRepository.updateFilters(_filters.value, newFilters, prop)
        isDataLoaded = false
        resetListState()
        if (prop !in listOf(
                TypeFilters.DEAL_TYPE.value,
                TypeFilters.LISTING_TYPE.value,
                TypeFilters.PROPERTY_TYPE.value
            )
        ) {
            debouncedSearch()
        }
    }

    private fun updateFilters(deal: Int, listing: Int, property: Int) {
        _dealType.value = deal
        _listingType.value = listing
        _propertyType.value = property

        resetNonMainFilters()

        updateSingleFilter(TypeFilters.DEAL_TYPE.value, deal)
        updateSingleFilter(TypeFilters.LISTING_TYPE.value, listing)
        updateSingleFilter(TypeFilters.PROPERTY_TYPE.value, property)

        getCountBySearchResult()
    }

    fun updateRangeFilter(prop: String, from: Int, to: Int) {
        val newFilters = Filters(mutableMapOf(prop to FilterValue.RangeValue(from, to)))
        updateFilters(newFilters, prop)
    }

    private fun updateSingleFilter(prop: String, value: Int) {
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
                val dealType =
                    (_filters.value.filterMap[TypeFilters.DEAL_TYPE.value] as? FilterValue.SingleValue)?.value ?: 1
                val listingType =
                    (_filters.value.filterMap[TypeFilters.LISTING_TYPE.value] as? FilterValue.SingleValue)?.value
                        ?: 1
                val propertyType =
                    (_filters.value.filterMap[TypeFilters.PROPERTY_TYPE.value] as? FilterValue.SingleValue)?.value
                        ?: 1

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
                val dealType =
                    (_filters.value.filterMap[TypeFilters.DEAL_TYPE.value] as? FilterValue.SingleValue)?.value ?: 1
                val listingType =
                    (_filters.value.filterMap[TypeFilters.LISTING_TYPE.value] as? FilterValue.SingleValue)?.value?: 1
                val propertyType =
                    (_filters.value.filterMap[TypeFilters.PROPERTY_TYPE.value] as? FilterValue.SingleValue)?.value?: 1
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



@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.FilterValue
import com.example.kilt.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    // Кэш для результатов поиска
    private var cachedSearchResults: PagingData<PropertyItem>? = null

    // Флаг для отслеживания, была ли загрузка данных
    private var isDataLoaded = false

    private val _searchResult = MutableStateFlow<SearchResponse?>(null)
    val searchResult: StateFlow<SearchResponse?> = _searchResult.asStateFlow()

    private val _searchResultCount = MutableStateFlow<String?>("0")
    val searchResultCount: StateFlow<String?> = _searchResultCount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    private val _error = MutableStateFlow<String?>(null)

    private val _filters = MutableStateFlow(Filters())
    val filters: StateFlow<Filters> = _filters

    private val _listState = MutableStateFlow(LazyListState())
    val listState: StateFlow<LazyListState> = _listState.asStateFlow()

    fun updateListState(state: LazyListState) {
        _listState.value = state
    }

    fun resetListState() {
        _listState.value = LazyListState()
    }

    val searchResults: Flow<PagingData<PropertyItem>> = filters.flatMapLatest { filters ->
        if (!isDataLoaded) {
            // Если данные ещё не загружены, загружаем и сохраняем их в кэш
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    initialLoadSize = 10,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { SearchPagingSource(searchRepository, filters) }
            ).flow.cachedIn(viewModelScope).also { flow ->
                viewModelScope.launch {
                    flow.collect { pagingData ->
                        cachedSearchResults = pagingData
                        isDataLoaded = true // Помечаем, что данные были загружены
                    }
                }
            }
        } else {
            // Если данные уже загружены, возвращаем кэшированные данные
            flowOf(cachedSearchResults ?: PagingData.empty())
        }
    }

    init {
        updateSingleFilter("deal_type", 1)
        updateSingleFilter("listing_type", 1)
        updateSingleFilter("property_type", 1)
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
        Log.d("SearchViewModel", "Filters updated: ${_filters.value}")
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
        Log.d("prop", "updateListFilter1111:$prop")
    }

    fun getPropertyById(id: String): PropertyItem? {
        return searchRepository.getPropertyById(id, _searchResult.value)
    }

    fun performSearch() {
        viewModelScope.launch {
            if (isDataLoaded) return@launch // Если данные уже загружены, не повторяем запрос

            _isLoading.value = true
            _error.value = null
            try {
                val request = searchRepository.createSearchRequest(_filters.value, 0, "new")
                Log.d("SearchViewModel", "Created search request: $request")
                val response = searchRepository.performSearch(request)
                Log.d("SearchViewModel", "Received search response: $response")
                _searchResult.value = response.copy(list = response.list.toList())
                isDataLoaded = true // Данные загружены
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



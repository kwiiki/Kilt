package com.example.kilt.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kilt.domain.search.usecase.GetListingByIdUseCase
import com.example.kilt.models.FilterValue
import com.example.kilt.models.Filters
import com.example.kilt.models.PropertyCoordinate
import com.example.kilt.models.PropertyItem
import com.example.kilt.domain.search.repository.SearchRepository
import com.example.kilt.domain.search.usecase.GetListingsByIdsUseCase
import com.example.kilt.domain.search.ListingPagination
import com.example.kilt.domain.search.PaginationByListingsId
import com.example.kilt.models.HomeSale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val filtersStateFlow: StateFlow<FiltersState>,
    private val sortingFlow: StateFlow<String>,
    private val getListingByIdUseCase: GetListingByIdUseCase,
    private val getListingsByIdsUseCase: GetListingsByIdsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _pointsFlow = MutableStateFlow<List<PropertyCoordinate>>(emptyList())
    val pointsFlow:StateFlow<List<PropertyCoordinate>> = _pointsFlow

    private val _searchResultsFlow = MutableStateFlow<PagingData<PropertyItem>>(PagingData.empty())
    val searchResultsFlow: StateFlow<PagingData<PropertyItem>> = _searchResultsFlow

    private val _listingsByIdsFlow = MutableStateFlow<PagingData<PropertyItem>>(PagingData.empty())
    val listingsByIdsFlow: StateFlow<PagingData<PropertyItem>> = _listingsByIdsFlow

    private val _listingFlow = MutableStateFlow<HomeSale?>(null)
    val listingFlow:StateFlow<HomeSale?> = _listingFlow

    init {
        observeFiltersAndSorting()
    }

    private fun observeFiltersAndSorting() {
        viewModelScope.launch {
            filtersStateFlow.combine(sortingFlow) { filtersState, sorting ->
                filtersState to sorting
            }.collect { (filtersState, sorting) ->
                performSearch(filtersState, sorting)
            }
        }
    }
    private fun getDealType(filtersState: FiltersState): Int =
        (filtersState.filters["deal_type"] as? FilterValue.SingleValue)?.value ?: 1

    private fun getPropertyType(filtersState: FiltersState): Int =
        (filtersState.filters["property_type"] as? FilterValue.SingleValue)?.value ?: 1

    private fun getListingType(filtersState: FiltersState): Int =
        (filtersState.filters["listing_type"] as? FilterValue.SingleValue)?.value ?: 1

    fun getListingsByIds(ids: List<Int>) {
        Log.d("pagingData", "getListingsByIds: ${ids.size}")
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false,
                        prefetchDistance = 1
                    ),
                    pagingSourceFactory = {
                        PaginationByListingsId(
                            idList = ids,
                            getListingsByIdsUseCase = getListingsByIdsUseCase
                        )
                    }
                )
                pager.flow.cachedIn(viewModelScope).collect { pagingData ->
                    _listingsByIdsFlow.value = pagingData
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("SearchResultsViewModel", "Error fetching listings by IDs", e)
                _error.value = e.message ?: "Unknown error occurred"
                _isLoading.value = false
            }
        }
    }
    fun getListingById(id: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val listing = getListingByIdUseCase.invoke(id)
                _listingFlow.value = listing
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("SearchResultsViewModel", "Error fetching listing by ID", e)
                _error.value = e.message ?: "Unknown error occurred"
                _isLoading.value = false
            }
        }
    }
    fun clearListing() {
        _listingFlow.value = null
    }
    fun performSearch(filtersState: FiltersState, sorting: String) {
        Log.d("searchResultViewModel", "performSearch: ${filtersState.filters}")
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val dealType = getDealType(filtersState)
                val listingType = getListingType(filtersState)
                val propertyType = getPropertyType(filtersState)
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false,
                        prefetchDistance = 1
                    ),
                    pagingSourceFactory = {
                        ListingPagination(
                            searchRepository = searchRepository,
                            filters = Filters(filtersState.filters),
                            dealType = dealType,
                            propertyType = propertyType,
                            listingType = listingType,
                            sort = sorting,
                            onPointsUpdated = { points ->
                                _pointsFlow.value = points
                                Log.d("points", "Updated points: ${points.size}")
                            }
                        )
                    }
                )
                pager.flow.cachedIn(viewModelScope).collect { pagingData ->
                    _searchResultsFlow.value = pagingData
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("SearchResultsViewModel", "Search error", e)
                _error.value = e.message ?: "Unknown error occurred"
                _isLoading.value = false
            }
        }
    }
    fun updateFiltersAndPerformSearch(filtersState: FiltersState, sorting: String) {
        performSearch(filtersState, sorting)
    }
}


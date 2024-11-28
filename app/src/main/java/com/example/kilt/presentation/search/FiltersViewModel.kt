package com.example.kilt.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.models.FilterValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor() : ViewModel() {

    private val _filtersState = MutableStateFlow(FiltersState())
    val filtersState: StateFlow<FiltersState> = _filtersState

    private val _sorting = MutableStateFlow("new") // Начальная сортировка
    val sorting: StateFlow<String> = _sorting

    fun getSelectedFilters(prop: String): StateFlow<List<Int>> {
        val selectedFilters = MutableStateFlow(
            (_filtersState.value.filters[prop] as? FilterValue.ListValue)?.values ?: emptyList()
        )
        viewModelScope.launch {
            filtersState.collect {
                selectedFilters.value =
                    (it.filters[prop] as? FilterValue.ListValue)?.values ?: emptyList()
            }
        }
        return selectedFilters
    }

    fun updateListFilter(prop: String, selectedFilters: List<Int>) {
        val updatedFilters = _filtersState.value.filters.toMutableMap()
        updatedFilters[prop] = FilterValue.ListValue(selectedFilters)
        _filtersState.value = _filtersState.value.copy(filters = updatedFilters)
    }

    fun getRangeFilterValues(prop: String): Pair<Long, Long> {
        val rangeValue = _filtersState.value.filters[prop] as? FilterValue.RangeValue
        return Pair(rangeValue?.from ?: 0L, rangeValue?.to ?: Long.MAX_VALUE)
    }

    fun updateSorting(newSorting: String) {
        _sorting.value = newSorting
    }

    fun updateRangeFilter(prop: String, from: Long, to: Long) {
        val updatedFilters = _filtersState.value.filters.toMutableMap()
        updatedFilters[prop] = FilterValue.RangeValue(from, to)
        _filtersState.value = _filtersState.value.copy(filters = updatedFilters)
    }

    fun updateMapLocation(prop:String,from: Double, to:Double){
        val updatedFilters = _filtersState.value.filters.toMutableMap()
        updatedFilters[prop] = FilterValue.RangeValueForMap(from, to)
        _filtersState.value = _filtersState.value.copy(filters = updatedFilters)
    }

    fun updateFilter(prop: String, value: FilterValue) {
        Log.d("FiltersViewModel", "Updating filter $prop to $value")

        if (prop in listOf("deal_type", "property_type", "listing_type")) {
            resetNonMainFilters()
        }
        val updatedFilters = _filtersState.value.filters.toMutableMap()
        updatedFilters[prop] = value

        _filtersState.value = FiltersState(updatedFilters)
        Log.d("FiltersViewModel", "Updated filters state: ${_filtersState.value.filters}")
    }

    fun clearFilters() {
        _filtersState.value = FiltersState()
    }

    private fun resetNonMainFilters() {
        val mainFilters = listOf("deal_type", "property_type", "listing_type")
        Log.d("FiltersViewModel", "Before resetNonMainFilters: ${_filtersState.value.filters}")

        val preservedFilters = _filtersState.value.filters.filterKeys { it in mainFilters }
        val clearedFilters = preservedFilters.toMutableMap()

        _filtersState.value = FiltersState(clearedFilters)

        Log.d("FiltersViewModel", "After resetNonMainFilters: ${_filtersState.value.filters}")
    }

    fun getDealType(): Int =
        (_filtersState.value.filters["deal_type"] as? FilterValue.SingleValue)?.value ?: 1

    fun getPropertyType(): Int =
        (_filtersState.value.filters["property_type"] as? FilterValue.SingleValue)?.value ?: 1

    fun getListingType(): Int =
        (_filtersState.value.filters["listing_type"] as? FilterValue.SingleValue)?.value ?: 1
}

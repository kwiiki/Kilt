package com.example.kilt.repository

import com.example.kilt.data.Count
import com.example.kilt.data.FilterValue
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.TConfig
import com.example.kilt.data.THomeSale
import com.example.kilt.network.ApiService


class SearchRepositoryImpl(private val apiService: ApiService) : SearchRepository {
    override suspend fun performSearch(request: THomeSale): SearchResponse {
        return apiService.search(request)
    }

    override suspend fun getResultBySearchCount(request: THomeSale): Count {
        return apiService.getSearchCount(request)
    }

    override fun getPropertyById(id: String, searchResult: SearchResponse?): PropertyItem? {
        return searchResult?.list?.find { it.id.toString() == id }
    }

    override fun updateFilters(
        currentFilters: Filters,
        newFilters: Filters,
        prop: String
    ): Filters {
        val updatedMap = currentFilters.filterMap.toMutableMap()
        updatedMap[prop] = newFilters.filterMap[prop] ?: return currentFilters
        return Filters(updatedMap)
    }

    override fun updateRangeFilter(
        currentFilters: Filters,
        prop: String,
        min: Int,
        max: Int
    ): Filters {
        val updatedMap = currentFilters.filterMap.toMutableMap()
        updatedMap[prop] = FilterValue.RangeValue(from = min, to = max)
        return Filters(updatedMap)
    }

    override fun updateListFilter(
        currentFilters: Filters,
        prop: String,
        selectedValues: List<Int>
    ): Filters {
        val updatedMap = currentFilters.filterMap.toMutableMap()
        updatedMap[prop] = FilterValue.ListValue(values = selectedValues)
        return Filters(updatedMap)
    }

    override fun createSearchRequest(filters: Filters, page: Int, sorting: String): THomeSale {
        val formattedFilterMap = filters.filterMap.mapValues { (_, value) ->
            when (value) {
                is FilterValue.SingleValue -> value.value // Извлекаем значение из SingleValue
                is FilterValue.ListValue -> value.values // Извлекаем список из ListValue
                is FilterValue.RangeValue -> mapOf(
                    "from" to value.from,
                    "to" to value.to
                ) // Cast RangeValue in Map
            }
        }
        return THomeSale(
            filters = formattedFilterMap, // Передаем упрощенный Map<String, Any> вместо Filters
            config = TConfig(), // Добавьте нужные поля для конфигурации
            page = page,
            sorting = sorting
        )
    }

}


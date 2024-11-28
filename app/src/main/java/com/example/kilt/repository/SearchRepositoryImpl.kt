package com.example.kilt.repository

import android.util.Log
import com.example.kilt.domain.config.repository.ConfigRepository
import com.example.kilt.models.Count
import com.example.kilt.models.FilterValue
import com.example.kilt.models.Filters
import com.example.kilt.models.PropertyItem
import com.example.kilt.models.SearchResponse
import com.example.kilt.models.THomeSale
import com.example.kilt.network.ApiService


class SearchRepositoryImpl(
    private val apiService: ApiService,
    private val configRepository: ConfigRepository,
    private val configHelper: ConfigHelper
) : SearchRepository {

    override suspend fun performSearch(request: THomeSale): SearchResponse {
        Log.d("SearchRepository", "Starting performSearch")
        Log.d("SearchRepository", "performSearch: $request")
        val response = apiService.search(request)
        Log.d("SearchRepository", "performSearch: $response")

        return response
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
        min: Long,
        max: Long
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


    override suspend fun createSearchRequest(
        filters: Map<String, FilterValue>,
        dealType: Int,
        propertyType: Int,
        listingType: Int,
        page: Int,
        sorting: String
    ): THomeSale {
        val config = configRepository.config
        val listingStructures = config.value?.listingStructures ?: emptyList()
        val propLabels = config.value?.propLabels ?: emptyList()

        val dynamicConfig = configHelper.createDynamicTConfig(
            dealType = dealType,
            propertyType = propertyType,
            listingType = listingType,
            listingStructures = listingStructures,
            propLabels = propLabels
        ).toMutableMap()

        dynamicConfig["kato_path"] = "like"
        dynamicConfig["lat"] = "range"
        dynamicConfig["lng"] = "range"

        // Форматирование фильтров
        val formattedFilters = filters.mapValues { (_, value) ->
            when (value) {
                is FilterValue.SingleValue -> value.value
                is FilterValue.ListValue -> value.values.takeIf { it.isNotEmpty() }
                is FilterValue.ListValue1 -> value.values.takeIf { it.isNotEmpty() }
                is FilterValue.RangeValueForMap -> mapOf(
                    "from" to value.from,
                    "to" to value.to
                ).filterValues { it != 0.0 }.takeIf { it.isNotEmpty() }

                is FilterValue.RangeValue -> mapOf(
                    "from" to value.from,
                    "to" to value.to
                ).filterValues { it != 0L }.takeIf { it.isNotEmpty() }
            }
        }.filterValues { value ->
            when (value) {
                is List<*> -> value.isNotEmpty()
                is Map<*, *> -> value.isNotEmpty()
                else -> value != null && value != 0
            }
        }

        // Формирование запроса
        return THomeSale(
            filters = formattedFilters,
            config = dynamicConfig,
            page = page,
            sorting = sorting
        )
    }
}


package com.example.kilt.domain.search.repository

import com.example.kilt.data.search.model.SearchByListingsIdRequest
import com.example.kilt.data.search.model.SearchByListingsIdResponse
import com.example.kilt.models.Count
import com.example.kilt.models.FilterValue
import com.example.kilt.models.Filters
import com.example.kilt.models.HomeSale
import com.example.kilt.models.PropertyItem
import com.example.kilt.models.SearchResponse
import com.example.kilt.models.THomeSale

interface SearchRepository {
    suspend fun performSearch(request: THomeSale): SearchResponse
    suspend fun getResultBySearchCount(request: THomeSale): Count
    fun getPropertyById(id: String, searchResult: SearchResponse?): PropertyItem?
    fun updateFilters(currentFilters: Filters, newFilters: Filters, prop: String): Filters
    fun updateRangeFilter(currentFilters: Filters, prop: String, min: Long, max: Long): Filters
    fun updateListFilter(currentFilters: Filters, prop: String, selectedValues: List<Int>): Filters
    suspend fun createSearchRequest(
        filters: Map<String, FilterValue>,
        dealType: Int,
        propertyType: Int,
        listingType: Int,
        page: Int,
        sorting: String
    ): THomeSale
    suspend fun getListingById(id:String): HomeSale
    suspend fun getListingsById(searchByListingsIdRequest: SearchByListingsIdRequest):SearchByListingsIdResponse
}
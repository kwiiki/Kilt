package com.example.kilt.repository

import com.example.kilt.data.Count
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale

interface SearchRepository {
    suspend fun performSearch(request: THomeSale): SearchResponse
    suspend fun getResultBySearchCount(request: THomeSale): Count
    fun getPropertyById(id: String, searchResult: SearchResponse?): PropertyItem?
    fun updateFilters(currentFilters: Filters, newFilters: Filters, prop: String): Filters
    fun updateRangeFilter(currentFilters: Filters, prop: String, min: Int, max: Int): Filters
    fun updateListFilter(currentFilters: Filters, prop: String, selectedValues: List<Int>): Filters
    fun createSearchRequest(filters: Filters, page: Int, sorting: String): THomeSale
}
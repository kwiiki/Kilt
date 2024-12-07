package com.example.kilt.viewmodels

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kilt.models.Filters
import com.example.kilt.models.PropertyItem
import com.example.kilt.models.SearchResponse
import com.example.kilt.repository.SearchRepository

class SearchPagingSource(
    private val searchRepository: SearchRepository,
    private val filters: Filters,
    private val dealType: Int,
    private val propertyType: Int,
    private val listingType: Int,
    private val sort: String
) : PagingSource<Int, PropertyItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PropertyItem> {
        val currentPage = params.key ?: 0
        Log.d("SearchPagingSource", "Loading page $currentPage with loadSize: ${params.loadSize}")
        return try {
            val request = searchRepository.createSearchRequest(
                filters = filters,
                dealType = dealType,
                propertyType = propertyType,
                listingType = listingType,
                page = currentPage,
                sorting = sort
            )

            val response = searchRepository.performSearch(request)
            val items = response.list
            val nextKey = if (items.isNotEmpty()) currentPage + 1 else null
            val prevKey = if (currentPage == 0) null else currentPage - 1

            LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e("SearchPagingSource", "Error loading page $currentPage", e)
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, PropertyItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}
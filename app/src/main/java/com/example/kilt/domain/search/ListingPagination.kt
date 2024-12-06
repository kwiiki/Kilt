package com.example.kilt.domain.search

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kilt.models.Filters
import com.example.kilt.models.PropertyCoordinate
import com.example.kilt.models.PropertyItem
import com.example.kilt.domain.search.repository.SearchRepository

class ListingPagination(
    private val searchRepository: SearchRepository,
    private val filters: Filters,
    private val dealType: Int,
    private val propertyType: Int,
    private val listingType: Int,
    private val sort: String,
    private val onPointsUpdated: (List<PropertyCoordinate>) -> Unit
) : PagingSource<Int, PropertyItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PropertyItem> {
        val page = params.key ?: 0
        Log.d("SearchPagingSource", "Loading page $page with filters: $filters and sorting: $sort")
        return try {
            val request = searchRepository.createSearchRequest(
                filters = filters.filterMap,
                dealType = dealType,
                propertyType = propertyType,
                listingType = listingType,
                page = page,
                sorting = sort
            )
            val response = searchRepository.performSearch(request)
            onPointsUpdated(response.map)

            LoadResult.Page(
                data = response.list,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.list.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("SearchPagingSource", "Error loading page $page", e)
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, PropertyItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

package com.example.kilt.viewmodels

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.repository.SearchRepository

class SearchPagingSource(
    private val searchRepository: SearchRepository,
    private val filters: Filters,
    private val dealType: Int,
    private val propertyType: Int,
    private val listingType: Int
) : PagingSource<Int, PropertyItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PropertyItem> {
        try {
            val currentPage = params.key ?: 1
            Log.d("SearchPagingSource", "Loading page $currentPage with loadSize: ${params.loadSize}")

            val request = searchRepository.createSearchRequest(
                filters = filters,
                dealType = dealType,
                propertyType = propertyType,
                listingType = listingType,
                page = currentPage,
                sorting = "new"
            )
            Log.d("request", "request: $request")
            val response = searchRepository.performSearch(request)
            Log.d("response", "load: $response")
            val items = response.list
            return LoadResult.Page(
                data = items,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (items.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            Log.e("SearchPagingSource", "Error loading page", e)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PropertyItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}
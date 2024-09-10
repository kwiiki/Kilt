package com.example.kilt.viewmodels

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kilt.data.Filters
import com.example.kilt.data.PropertyItem
import com.example.kilt.repository.SearchRepository

class SearchPagingSource(
    private val searchRepository: SearchRepository,
    private val filters: Filters
) : PagingSource<Int, PropertyItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PropertyItem> {
        return try {
            val page = params.key ?: 1
            val request = searchRepository.createSearchRequest(filters, page, "new")
            val response = searchRepository.performSearch(request)

            LoadResult.Page(
                data = response.list,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (response.list.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
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
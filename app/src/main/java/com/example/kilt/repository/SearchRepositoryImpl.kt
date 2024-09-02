package com.example.kilt.repository

import com.example.kilt.data.Count
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale
import com.example.kilt.network.ApiService

class SearchRepositoryImpl(private val apiService: ApiService) : SearchRepository {
    override suspend fun performSearch(request: THomeSale): SearchResponse {
        return apiService.search(request)
    }

    override suspend fun getResultBySearchCount(request: THomeSale): Count {
        return apiService.getSearchCount(request)
    }
}
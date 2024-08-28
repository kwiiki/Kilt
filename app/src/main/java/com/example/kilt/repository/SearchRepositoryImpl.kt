package com.example.kilt.repository

import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale
import com.example.kilt.network.ApiService
import com.example.myapplication.data.HomeSale

class SearchRepositoryImpl(private val apiService: ApiService) : SearchRepository {
    override suspend fun performSearch(request: THomeSale): SearchResponse {
        return apiService.search(request)
    }
}
package com.example.kilt.repository

import com.example.kilt.data.SearchResponse
import com.example.kilt.data.THomeSale

interface SearchRepository {
    suspend fun performSearch(request: THomeSale): SearchResponse
}
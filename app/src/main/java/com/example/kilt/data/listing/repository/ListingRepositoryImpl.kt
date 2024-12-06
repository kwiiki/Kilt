package com.example.kilt.data.listing.repository

import android.util.Log
import com.example.kilt.domain.listing.repository.ListingRepository
import com.example.kilt.core.network.ApiService
import com.example.kilt.models.HomeSale

class ListingRepositoryImpl(
    private val apiService: ApiService
) : ListingRepository {
    override suspend fun fetchHomeSale(id: String): HomeSale {
        Log.d("repo id", "fetchHomeSale: $id")
        return apiService.getListingById(id)
    }
}
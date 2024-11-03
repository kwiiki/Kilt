package com.example.kilt.repository

import android.util.Log
import com.example.kilt.network.ApiService
import com.example.kilt.models.HomeSale

class HomeSaleRepositoryImpl(
    private val apiService: ApiService
) : HomeSaleRepository {
    override suspend fun fetchHomeSale(id: String): HomeSale {
        Log.d("repo id", "fetchHomeSale: $id")
        return apiService.getHomeSale(id)
    }
}
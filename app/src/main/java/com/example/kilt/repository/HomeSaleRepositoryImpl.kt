package com.example.kilt.repository

import android.util.Log
import com.example.kilt.data.Config
import com.example.kilt.network.ApiService
import com.example.myapplication.data.HomeSale
import javax.inject.Inject

class HomeSaleRepositoryImpl(
    private val apiService: ApiService
) : HomeSaleRepository {
    override suspend fun fetchHomeSale(id: String): HomeSale {
        Log.d("repo id", "fetchHomeSale: $id")
        return apiService.getHomeSale(id)
    }

    override suspend fun fetchConfig(): Config {
        return apiService.getConfig()
    }
}
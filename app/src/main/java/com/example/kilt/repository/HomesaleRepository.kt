package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.kilt.network.RetrofitInstance
import com.example.myapplication.data.HomeSale

class HomeSaleRepository {
    suspend fun fetchHomeSale(): HomeSale {
        return RetrofitInstance.api.getHomeSale()
    }
    suspend fun fetchConfig():Config{
        return RetrofitInstance.api.getConfig()
    }
}

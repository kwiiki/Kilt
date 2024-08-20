package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.kilt.network.ApiService
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(private val apiService: ApiService) : ConfigRepository {

    override suspend fun getConfig():Config {
        return apiService.getConfig()
    }

    override suspend fun getListingProps(): List<String> {
        // Здесь должна быть реализация получения списка свойств
        return listOf("floor", "num_rooms", "furniture_list")
    }
}
package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.kilt.network.ApiService
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val flatRepository: FlatRepository
) : ConfigRepository {

    override suspend fun getConfig(): Config {
        return apiService.getConfig()
    }

    override suspend fun getListingProps(dealType: Int, listingType: Int, propertyType: Int): List<String>? {
        val config = getConfig()
        return flatRepository.getListingProps(
            dealType,
            listingType,
            propertyType,
            config.listingStructures ?: emptyList()
        )
    }
}
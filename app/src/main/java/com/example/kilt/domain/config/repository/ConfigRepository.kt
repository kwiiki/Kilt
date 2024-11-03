package com.example.kilt.domain.config.repository

import com.example.kilt.models.Config
import kotlinx.coroutines.flow.StateFlow

interface ConfigRepository {
    val config: StateFlow<Config?>
    suspend fun loadConfig()
    fun getConfig(): Config?
    fun getListingProps(dealType: Int, listingType: Int, propertyType: Int): List<String>?
    fun getListingTops(dealType: Int, listingType: Int, propertyType: Int): List<String>?
    fun getListingInfo(dealType: Int,listingType: Int,propertyType: Int):List<String>?
}
package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.kilt.data.config.ListingStructures
import kotlinx.coroutines.flow.StateFlow

interface ConfigRepository {
    val config: StateFlow<Config?>
    suspend fun loadConfig()
    fun getConfig(): Config?
    fun getListingProps(dealType: Int, listingType: Int, propertyType: Int): List<String>?
    fun getListingTops(dealType: Int, listingType: Int, propertyType: Int): List<String>?
    fun getListingInfo(dealType: Int,listingType: Int,propertyType: Int):List<String>?
}
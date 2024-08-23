package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.kilt.data.config.ListingStructures

interface ConfigRepository {
    suspend fun getConfig(): Config
    suspend fun getListingProps(dealType: Int, listingType: Int, propertyType: Int): List<String>?
}
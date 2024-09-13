package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.kilt.data.config.ListingStructures
import com.example.kilt.network.ApiService
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ConfigRepository {

    override suspend fun getConfig(): Config {
        return apiService.getConfig()
    }

    override suspend fun getListingProps(
        dealType: Int,
        listingType: Int,
        propertyType: Int
    ): List<String>? {
        val config = getConfig().listingStructures
        return config.find { structure ->
            structure.deal_type == dealType &&
                    structure.listing_type == listingType &&
                    structure.property_type == propertyType
        }?.props?.split(",")
    }

    override suspend fun getListingTops(
        dealType: Int,
        listingType: Int,
        propertyType: Int
    ): List<String>? {
        val config = getConfig().listingStructures
        return config.find { top ->
            top.deal_type == dealType &&
                    top.listing_type == listingType &&
                    top.property_type == propertyType
        }?.top?.split(",")
    }
}

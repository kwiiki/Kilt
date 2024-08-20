package com.example.kilt.repository

import com.example.kilt.data.config.ListingStructures

interface FlatRepository {
    fun getListingProps(
        dealType: Int,
        listingType: Int,
        propertyType: Int,
        listings: List<ListingStructures>
    ): List<String>?
}
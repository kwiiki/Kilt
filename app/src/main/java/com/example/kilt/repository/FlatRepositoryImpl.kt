package com.example.kilt.repository

import com.example.kilt.data.config.ListingStructures
import javax.inject.Inject

class FlatRepositoryImpl @Inject constructor() : FlatRepository {
    override fun getListingProps(
        dealType: Int,
        listingType: Int,
        propertyType: Int,
        listings: List<ListingStructures>
    ): List<String>? {
        return listings.find { structure ->
            structure.deal_type == dealType &&
                    structure.listing_type == listingType &&
                    structure.property_type == propertyType
        }?.props?.split(",")
    }
}
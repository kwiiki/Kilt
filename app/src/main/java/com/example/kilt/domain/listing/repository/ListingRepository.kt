package com.example.kilt.domain.listing.repository

import com.example.kilt.models.HomeSale

interface ListingRepository {
    suspend fun fetchHomeSale(id: String): HomeSale
}
package com.example.kilt.data.userabout.repository

import com.example.kilt.data.userabout.dto.toListingList
import com.example.kilt.domain.userabout.model.ListingList
import com.example.kilt.domain.userabout.repository.UserAboutRepository
import com.example.kilt.network.ApiService
import javax.inject.Inject

class UserAboutRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserAboutRepository {
    override suspend fun getUserListings(id: String): ListingList {
        val listingsDTO = apiService.getUserListings(id = id)
        return listingsDTO.toListingList()
    }
}
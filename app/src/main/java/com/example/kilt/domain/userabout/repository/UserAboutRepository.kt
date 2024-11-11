package com.example.kilt.domain.userabout.repository

import com.example.kilt.domain.userabout.model.ListingList

interface UserAboutRepository {
    suspend fun getUserListings(id:String): ListingList
}
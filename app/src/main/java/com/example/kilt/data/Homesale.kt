package com.example.myapplication.data

import com.example.kilt.data.Filters
import com.example.kilt.data.Listing


data class HomeSale(
    val filters: Filters,
    val listing: Listing,
    val page: Int,
    val sorting: String
)
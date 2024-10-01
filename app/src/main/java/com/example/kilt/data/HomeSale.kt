package com.example.kilt.data

data class HomeSale(
    val filters: Filters,
    val listing: Listing,
    val page: Int,
    val sorting: String
)
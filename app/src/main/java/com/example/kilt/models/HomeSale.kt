package com.example.kilt.models

data class HomeSale(
    val filters: Filters,
    val listing: Listing,
    val page: Int,
    val sorting: String
)
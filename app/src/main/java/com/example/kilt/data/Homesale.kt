package com.example.myapplication.data

import com.example.kilt.data.Listing


data class HomeSale(
    val listing: Listing,
    val page: Int,
    val sorting: String
)
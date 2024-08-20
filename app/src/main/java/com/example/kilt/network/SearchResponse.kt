package com.example.kilt.network

data class SearchResponse(
    val results: List<ResultItem>,
    val totalResults: Int,
    val currentPage: Int,
    val totalPages: Int,
    val success: Boolean,
    val message: String?
)

data class ResultItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val location: Location,
    val images: List<String>,
    val isFavorite: Boolean,
    val createdAt: String
)

data class Location(
    val latitude: Double,
    val longitude: Double
)
package com.example.kilt.data



data class THomeSale(
    val filters: Map<String, Any>,
    val config: Map<String, String>,
    val page: Int,
    val sorting: String
)

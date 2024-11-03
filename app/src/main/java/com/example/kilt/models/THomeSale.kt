package com.example.kilt.models



data class THomeSale(
    val filters: Map<String, Any>,
    val config: Map<String, String>,
    val page: Int,
    val sorting: String
)

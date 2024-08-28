package com.example.kilt.data

data class THomeSale(
    val filters: Filters,
    val config: TConfig,
    val page: Int,
    val sorting: String
)

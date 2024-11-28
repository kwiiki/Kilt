package com.example.kilt.presentation.search

import com.example.kilt.models.FilterValue

data class FiltersState(
    val filters: MutableMap<String, FilterValue> = mutableMapOf()
)
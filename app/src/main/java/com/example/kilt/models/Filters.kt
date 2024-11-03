package com.example.kilt.models

data class Filters(
    val filterMap: MutableMap<String, FilterValue> = mutableMapOf()
)


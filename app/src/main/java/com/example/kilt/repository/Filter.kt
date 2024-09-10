package com.example.kilt.repository

data class Filter(
    val filters: Map<String, FilterValue> = mutableMapOf<String, FilterValue>()
) {
    fun addFilter(type: String, value: FilterValue) =
        copy(filters = filters + (type to value))

    fun removeFilter(type: String) =
        copy(filters = filters - type)

    fun getFilter(type: String): FilterValue? =
        filters[type]
}

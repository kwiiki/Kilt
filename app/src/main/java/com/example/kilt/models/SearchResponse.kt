package com.example.kilt.models


data class SearchResponse(
    val list: List<PropertyItem>,
    val map:List<PropertyCoordinate>
)
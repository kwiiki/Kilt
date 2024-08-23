package com.example.kilt.data

import com.example.kilt.data.config.Advanced
import com.example.kilt.data.config.Conveniences
import com.example.kilt.data.config.Infrastructures
import com.example.kilt.data.config.ListingStructures
import com.example.kilt.data.config.PropMapping
import com.example.kilt.data.config.PropertyTypeX

data class Config(
    val advanced: Advanced,
    val conveniencesList: List<Conveniences>,
    val infrastructuresList: List<Infrastructures>,
    val listingStructures: List<ListingStructures>,
    val propertyTypes: List<PropertyTypeX>,
    val rawValues: List<String>,
    val tableValues: List<String>,
    val propMapping: PropMapping,
    val propLabels : List<PropLabel>
)
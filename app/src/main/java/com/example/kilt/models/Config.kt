package com.example.kilt.models

import com.example.kilt.models.config.Advanced
import com.example.kilt.models.config.Conveniences
import com.example.kilt.models.config.Infrastructures
import com.example.kilt.models.config.ListingStructures
import com.example.kilt.models.config.PropMapping
import com.example.kilt.models.config.PropertyTypeX

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
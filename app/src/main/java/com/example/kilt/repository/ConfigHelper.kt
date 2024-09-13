package com.example.kilt.repository

import com.example.kilt.data.PropLabel
import com.example.kilt.data.TConfig
import com.example.kilt.data.config.ListingStructures

class ConfigHelper {
    fun getFieldType(fieldName: String, propLabels: List<PropLabel>): String {
        val propLabel = propLabels.find { it.property == fieldName }
        return when {
            propLabel != null -> {
                when (propLabel.filter_type) {
                    "range" -> "range"
                    "list" -> "list"
                    "search" -> "search"
                    "list-multiple" -> "list-multiple"
                    else -> "list" // default to list for any other cases
                }
            }
            fieldName.endsWith("_list") -> "list-multiple"
            fieldName == "kato_path" -> "like"
            else -> "list"
        }
    }

    fun createDynamicTConfig(
        dealType: Int,
        propertyType: Int,
        listingType: Int,
        listingStructures: List<ListingStructures>,
        propLabels: List<PropLabel>
    ): TConfig {
        val matchingStructure = listingStructures.find {
            it.deal_type == dealType &&
                    it.property_type == propertyType &&
                    it.listing_type == listingType
        }
        if (matchingStructure == null) {
            return TConfig()
        }
        val props = matchingStructure.props.split(",")
        val configFields = props.associateWith { prop -> getFieldType(prop, propLabels) }
        return TConfig(fields = configFields)
    }
}
package com.example.kilt.data.search.repository

import android.util.Log
import com.example.kilt.models.PropLabel
import com.example.kilt.models.config.ListingStructures

class ConfigHelper {
    private fun getFieldType(fieldName: String, propLabels: List<PropLabel>): String {
        val propLabel = propLabels.find { it.property == fieldName }
        return when {
            propLabel != null -> {
                when (propLabel.filter_type) {
                    "range" -> "range"
                    "list-multiple" -> "list-multiple"
                    "list" -> "list"
                    "search" -> "search"
                    "residential-complex" -> "residential-complex"
                    else -> "list-multiple" // default to list for any other cases
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
    ): Map<String, String> {
        val matchingStructure = listingStructures.find {
            it.deal_type == dealType &&
                    it.property_type == propertyType &&
                    it.listing_type == listingType
        }
        Log.d("kato_path", "createDynamicTConfig: $matchingStructure")
        if (matchingStructure == null) {
            return emptyMap()
        }
        val props = matchingStructure.props.split(",")
        return props.associateWith { prop -> getFieldType(prop, propLabels) }
    }
}
package com.example.kilt.data

import com.example.kilt.data.config.Floor

data class TConfig(
    val residential_complex: String = "residential-complex",
    val num_rooms: String? = "list",
    val price: String = "range",
    val rent_period:String = "list",
    val built_year: String = "range",
    val construction_type: String = "list",
    val floor: List<String> = listOf(),
    val num_floors: String = "list",
    val furniture: String = "list",
    val area: String = "",
    val kitchen_area: String = "",
    val is_bailed: String = "",
    val former_dormitory: String = "",
    val bathroom: String = "",
    val internet: String = "",
    val balcony: String = "",
    val balcony_glass: String = "",
    val door: String = "",
    val parking: String = "",
    val floor_material: String = "",
    val security: String = "",
    val status: String = "",
    val user_type: String = "",
    val kato_path: String = "",
    val lat: String = "",
    val lng: String = ""
)

package com.example.kilt.data

import com.example.kilt.data.config.Price

data class Filters(
    val deal_type: Int? = null,
    val property_type: Int? = null,
    val listing_type: Int? = null,
    val price: Price? = null,
    val num_rooms: List<Int>? = null,
    val status: Int? = null,
    val floor: List<Int>? = null,
    val area: Area? = null,
    val rent_period: List<Int>? = null,
    val furniture_list: List<Int>? = null
//    val new_conveniences:List<Int>,
//    val toilet_separation:List<Int>
//    val conveniences: List<Int>,

//    val lat: LatLng,
//    val lng: LatLng
)
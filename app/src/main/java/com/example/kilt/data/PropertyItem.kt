package com.example.kilt.data

import com.example.kilt.data.config.Image

data class PropertyItem(
    val id: Int,
    val deal_type: Int,
    val listing_type: Int,
    val property_type: Int,
    val num_rooms: Int,
    val area: Double,
    val floor: Int,
    val num_floors: Int?,
    val rent_period: Int,
    val living_area:Int,
    val price: String,
    val address_string: String,
    val description: String? = null,
    val seller_whatsapp: String,
    val seller_phone_number: String,
    val user_type: Int,
    val status: Int,
    val first_image: String,
    val first_image_cloud: Int,
    val lat: Double,
    val lng: Double,
    val type: String,
    val images: List<Image>
)
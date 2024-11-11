package com.example.kilt.data.userabout.dto

import com.example.kilt.domain.userabout.model.Listing
import com.example.kilt.domain.userabout.model.ListingList

data class ListingsDTO(
    val listings: List<Listings>,
    val offers: List<String>
)

fun ListingsDTO.toListingList(): ListingList {
    return ListingList(
        listing = listings.map { listing ->
            Listing(
                id = listing.id.toString(),
                price = listing.price,
                numFloor = listing.floor,
                numFloors = listing.num_floors,
                numRooms = listing.num_rooms,
                area = listing.area,
                address = listing.address_string as? String?:"",
                image = listing.first_image as? String ?:" "

            )
        }
    )
}

package com.example.kilt.repository

import com.example.kilt.data.Area
import com.example.kilt.data.BuiltYear
import com.example.kilt.data.Count
import com.example.kilt.data.DedicatedPower
import com.example.kilt.data.Filters
import com.example.kilt.data.KitchenArea
import com.example.kilt.data.LandArea
import com.example.kilt.data.LivingArea
import com.example.kilt.data.NumFloors
import com.example.kilt.data.NumToilets
import com.example.kilt.data.PropertyItem
import com.example.kilt.data.SearchResponse
import com.example.kilt.data.TConfig
import com.example.kilt.data.THomeSale
import com.example.kilt.data.config.Price
import com.example.kilt.network.ApiService


class SearchRepositoryImpl(private val apiService: ApiService) : SearchRepository {

    override suspend fun performSearch(request: THomeSale): SearchResponse {
        return apiService.search(request)
    }
    override suspend fun getResultBySearchCount(request: THomeSale): Count {
        return apiService.getSearchCount(request)
    }
    override fun getPropertyById(id: String, searchResult: SearchResponse?): PropertyItem? {
        return searchResult?.list?.find { it.id.toString() == id }
    }
    override fun updateFilters(currentFilters: Filters, newFilters: Filters, prop: String): Filters {
        return currentFilters.copy(
            deal_type = newFilters.deal_type,
            property_type = newFilters.property_type,
            listing_type = newFilters.listing_type,
            price = newFilters.price,
            num_rooms = newFilters.num_rooms,
            floor = newFilters.floor,
            area = newFilters.area,
            rent_period = newFilters.rent_period,
            furniture_list = newFilters.furniture_list,
            security = newFilters.security,
            toilet_separation = newFilters.toilet_separation,
            num_toilets = newFilters.num_toilets,
            new_conveniences = newFilters.new_conveniences,
            bathroom_inside = newFilters.new_conveniences,
            new_balcony = newFilters.new_balcony,
            loggia = newFilters.loggia,
            windows = newFilters.windows,
            suits_for = newFilters.suits_for,

            built_year = newFilters.built_year,
            construction_type = newFilters.construction_type,
            num_floors = newFilters.num_floors,
            furniture = newFilters.furniture,
            kitchen_area = newFilters.kitchen_area,
            is_bailed = newFilters.is_bailed,
            former_dormitory = newFilters.former_dormitory,
            bathroom = newFilters.bathroom,
            internet = newFilters.internet,
            balcony = newFilters.balcony,
            balcony_glass = newFilters.balcony_glass,
            door = newFilters.door,
            parking = newFilters.parking,
            floor_material = newFilters.floor_material,

            land_area = newFilters.land_area,
            living_area = newFilters.living_area,
            condition = newFilters.condition,
            heating = newFilters.heating,
            sewer = newFilters.sewer,
            drinking_water = newFilters.drinking_water,
            irrigation_water = newFilters.irrigation_water,
            electricity = newFilters.electricity,
            designation = newFilters.designation,
            where_located = newFilters.where_located,
            has_renters = newFilters.has_renters,
            free_planning = newFilters.free_planning,
            business_condition = newFilters.business_condition,
            line_of_houses = newFilters.line_of_houses,
            business_parking = newFilters.business_parking,
            business_entrance = newFilters.business_entrance,
            communications = newFilters.communications,
            dedicated_power = newFilters.dedicated_power

        )
    }
    override fun updateRangeFilter(currentFilters: Filters, prop: String, min: Int, max: Int): Filters {
        return when (prop) {
            "price" -> currentFilters.copy(price = Price(from = min, to = max))
            "area" -> currentFilters.copy(area = Area(from = min, to = max))
            "num_toilets" -> currentFilters.copy(num_toilets = NumToilets(from = min, to = max))
            "built_year" -> currentFilters.copy(built_year = BuiltYear(from = min, to = max))
            "num_floors" -> currentFilters.copy(num_floors = NumFloors(from = min, to = max))
            "kitchen_area" ->currentFilters.copy(kitchen_area = KitchenArea(from = min,to = max))
            "land_area" -> currentFilters.copy(land_area = LandArea(from = min, to = max))
            "living_area" -> currentFilters.copy(living_area = LivingArea(from = min, to = max))
            "dedicated_power" -> currentFilters.copy(dedicated_power = DedicatedPower(from = min, to = max))
            else -> currentFilters
        }
    }
    override fun updateListFilter(currentFilters: Filters, prop: String, selectedValues: List<Int>): Filters {
        return when (prop) {
            "num_rooms" -> currentFilters.copy(num_rooms = selectedValues)
            "floor" -> currentFilters.copy(floor = selectedValues)
            "rent_period" -> currentFilters.copy(rent_period = selectedValues)
            "security" -> currentFilters.copy(security = selectedValues)
            "furniture_list" -> currentFilters.copy(furniture_list = selectedValues)
            "toilet_separation" -> currentFilters.copy(toilet_separation = selectedValues)
            "new_conveniences" -> currentFilters.copy(new_conveniences = selectedValues)
            "bathroom_inside" -> currentFilters.copy(bathroom_inside = selectedValues)
            "new_balcony" -> currentFilters.copy(new_balcony = selectedValues)
            "loggia" -> currentFilters.copy(loggia = selectedValues)
            "windows" -> currentFilters.copy(windows = selectedValues)
            "suits_for" ->currentFilters.copy(suits_for = selectedValues)
            "construction_type" -> currentFilters.copy(construction_type = selectedValues)
            "furniture" -> currentFilters.copy(furniture = selectedValues)
            "is_bailed" -> currentFilters.copy(is_bailed = selectedValues)
            "former_dormitory" -> currentFilters.copy(former_dormitory = selectedValues)
            "bathroom" -> currentFilters.copy(bathroom = selectedValues)
            "internet" -> currentFilters.copy(internet = selectedValues)
            "balcony" -> currentFilters.copy(balcony = selectedValues)
            "balcony_glass" -> currentFilters.copy(balcony_glass = selectedValues)
            "door" -> currentFilters.copy(door = selectedValues)
            "parking" ->currentFilters.copy(parking =selectedValues)
            "floor_material" -> currentFilters.copy(floor_material = selectedValues)
            "condition" -> currentFilters.copy(condition = selectedValues)
            "heating" -> currentFilters.copy(heating = selectedValues)
            "sewer" -> currentFilters.copy(sewer = selectedValues)
            "drinking_water" -> currentFilters.copy(drinking_water = selectedValues)
            "irrigation_water" -> currentFilters.copy(irrigation_water = selectedValues)
            "electricity" -> currentFilters.copy(electricity = selectedValues)
            "telephone" -> currentFilters.copy(telephone = selectedValues)
            "designation" -> currentFilters.copy(designation = selectedValues)
            "where_located" -> currentFilters.copy(where_located = selectedValues)
            "has_renters" -> currentFilters.copy(has_renters = selectedValues)
            "free_planning" -> currentFilters.copy(free_planning = selectedValues)
            "business_condition" ->currentFilters.copy(business_condition = selectedValues)
            "line_of_houses" -> currentFilters.copy(line_of_houses = selectedValues)
            "business_parking" -> currentFilters.copy(business_parking = selectedValues)
            "business_entrance" -> currentFilters.copy(business_entrance = selectedValues)
            "communications" -> currentFilters.copy(communications = selectedValues)
            else -> currentFilters
        }
    }
    override fun createSearchRequest(filters: Filters, page: Int, sorting: String): THomeSale {
        return THomeSale(
            filters = Filters(
                deal_type = filters.deal_type,
                listing_type = filters.listing_type,
                property_type = filters.property_type,
                furniture_list = filters.furniture_list,
                num_rooms = filters.num_rooms,
                floor = filters.floor,
                toilet_separation = filters.toilet_separation,
                security = filters.security,
                price = filters.price,
                area = filters.area,
                rent_period = filters.rent_period,

                new_conveniences = filters.new_conveniences,
                bathroom_inside = filters.new_conveniences,
                num_toilets = filters.num_toilets,
                new_balcony = filters.new_balcony,
                loggia = filters.loggia,
                windows = filters.windows,
                suits_for = filters.suits_for,

                construction_type = filters.construction_type,
                built_year = filters.built_year,
                furniture = filters.furniture,
                kitchen_area = filters.kitchen_area,
                is_bailed = filters.is_bailed,
                num_floors = filters.num_floors,
                former_dormitory = filters.former_dormitory,
                bathroom = filters.bathroom,
                internet = filters.internet,
                balcony = filters.balcony,
                balcony_glass = filters.balcony_glass,
                door = filters.door,
                parking = filters.parking,
                floor_material =filters.floor_material,

                land_area = filters.land_area,
                living_area = filters.living_area,
                condition = filters.condition,
                heating = filters.heating,
                sewer = filters.sewer,
                drinking_water = filters.drinking_water,
                irrigation_water = filters.irrigation_water,
                electricity = filters.electricity,

                where_located = filters.where_located,
                designation = filters.designation,
                has_renters = filters.has_renters,
                free_planning = filters.free_planning,
                business_condition = filters.business_condition,
                line_of_houses = filters.line_of_houses,
                business_parking = filters.business_parking,
                business_entrance = filters.business_entrance,
                communications = filters.communications,
                dedicated_power = filters.dedicated_power
            ),
            config = TConfig(),
            page = page,
            sorting = sorting
        )
    }
}


package com.example.kilt.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.Config
import com.example.kilt.repository.FlatRepositoryImpl
import com.example.kilt.repository.HomeSaleRepository
import com.example.myapplication.data.HomeSale
import kotlinx.coroutines.launch

class HomeSaleViewModel : ViewModel() {
    private val repository = HomeSaleRepository()

    var homeSale = mutableStateOf<HomeSale?>(null)
        private set
    var config = mutableStateOf<Config?>(null)

    var topListings = mutableStateOf<List<String>>(emptyList())
        private set

    fun getListingProps(): List<String>? {
        val flat = FlatRepositoryImpl()
        val t = config.value?.listingStructures?.let { listingStructures ->
            flat.getListingProps(1, 2, 6, listingStructures)
        }
        Log.d("wwww", "loadHomeSale: $t")
        return t
    }

    fun loadHomeSale() {
        viewModelScope.launch {
            try {
                homeSale.value = repository.fetchHomeSale()
                config.value = repository.fetchConfig()

                Log.d("www", "loadHomesale: ${homeSale.value}")
                Log.d("ww", "loadConfig: ${config.value?.listingStructures}")
                Log.d("w1","probLabels: ${config.value?.propLabels?.size}")
                Log.d("w2", "loadHomeSale: ${config.value?.propMapping?.furniture_list?.list?.get(0)}")
                val flat = FlatRepositoryImpl()
                val t = config.value?.listingStructures?.let { listingStructures ->
                    flat.getListingProps(1, 1, 2, listingStructures)
                }
                Log.d("w3", "loadHomeSale: $t")


                updateTopListings()
            } catch (e: Exception) {
            }
        }
    }

    private fun updateTopListings() {
        val homeSaleValue = homeSale.value
        val listingStructures = config.value?.listingStructures ?: emptyList()

        if (homeSaleValue != null) {
            val topValues = listingStructures.filter { structure ->
                structure.deal_type == homeSaleValue.listing.deal_type &&
                        structure.listing_type == homeSaleValue.listing.listing_type &&
                        structure.property_type == homeSaleValue.listing.property_type
            }.flatMap { it.top.split(",") }

            topListings.value = topValues.distinct()
        }
        Log.d("HomeSaleViewModel", "updateTopListings: ${topListings.value}")
        Log.d("HomeSaleViewModel1", "updateTopListings: ${homeSaleValue}")
    }
}
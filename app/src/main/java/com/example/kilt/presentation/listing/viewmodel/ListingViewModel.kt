package com.example.kilt.presentation.listing.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.models.Config
import com.example.kilt.domain.config.repository.ConfigRepository
import com.example.kilt.domain.listing.repository.ListingRepository
import com.example.kilt.models.HomeSale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    private val configRepository: ConfigRepository
) : ViewModel() {

    private val _home = MutableStateFlow<HomeSale?>(null)
    val home: StateFlow<HomeSale?> = _home.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _configHome = MutableStateFlow<Config?>(null)
    val configHome: StateFlow<Config?> = _configHome.asStateFlow()


    var homeSale = mutableStateOf<HomeSale?>(null)
        private set
    var config = mutableStateOf<Config?>(null)

    private var isConfigLoaded = false

    var topListings = mutableStateOf<List<String>>(emptyList())
        private set

    init {
        loadHomeSale()
    }

    fun loadHomeSale() {
        viewModelScope.launch {
            try {
                config.value = configRepository.config.value
                updateTopListings()
            } catch (e: Exception) {
                _error.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun loadById(id: String) {
        viewModelScope.launch {
            val homeSaleDeferred = async { listingRepository.fetchHomeSale(id) }
            val configDeferred = async { configRepository.config.value }
            _isLoading.value = true

            try {
                val homeSale = homeSaleDeferred.await()
                val config = configDeferred.await()

                _home.value = homeSale
                _configHome.value = config
                updateTopListings()
            } catch (e: Exception) {
                _error.value = e.message ?: "An unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

     fun loadConfig() {
        viewModelScope.launch {
            if (!isConfigLoaded) {
                try {
                    Log.d("ConfigDownload", "getConfig: Download Config1")
                    // Load the config only if it's not loaded yet
                    if (configRepository.config.value == null) {
                        configRepository.loadConfig() // Only call if config is null
                    }
                    _configHome.value = configRepository.config.value
                    updateTopListings()
                    isConfigLoaded = true // Set flag to true after loading config
                } catch (e: Exception) {
                    _error.value = e.message ?: "An unknown error occurred while fetching config"
                }
            }
        }
    }

    private fun updateTopListings() {
        val homeSaleValue = homeSale.value
        val listingStructures = configRepository.config.value?.listingStructures

        if (homeSaleValue != null && listingStructures != null) {
            val topValues = listingStructures.filter { structure ->
                structure.deal_type == homeSaleValue.listing.deal_type &&
                        structure.listing_type == homeSaleValue.listing.listing_type &&
                        structure.property_type == homeSaleValue.listing.property_type
            }.flatMap { it.top.split(",") }

            topListings.value = topValues.distinct() // Avoid empty list reassignment
        } else {
            topListings.value = emptyList()
        }
    }
}
package com.example.kilt.repository

import android.util.Log
import com.example.kilt.data.Config
import com.example.kilt.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ConfigRepository {
    private val _config = MutableStateFlow<Config?>(null)
    override val config: StateFlow<Config?> = _config.asStateFlow()

    override suspend fun loadConfig() {
        if (_config.value == null) {
            Log.d("ConfigDownload", "loadConfig: $_config")
            _config.value = apiService.getConfig()
        }
    }

    override fun getConfig(): Config? {
        Log.d("ConfigDownload", "getConfig: Download Config")
        return _config.value
    }

    override fun getListingProps(
        dealType: Int,
        listingType: Int,
        propertyType: Int
    ): List<String>? {
        return config.value?.listingStructures?.find { structure ->
            structure.deal_type == dealType &&
                    structure.listing_type == listingType &&
                    structure.property_type == propertyType
        }?.props?.split(",")
    }

    override fun getListingTops(dealType: Int, listingType: Int, propertyType: Int): List<String>? {
        Log.d("ConfigDownload", "getListingTops: $config")
        return config.value?.listingStructures?.find { top ->
            top.deal_type == dealType &&
                    top.listing_type == listingType &&
                    top.property_type == propertyType
        }?.top?.split(",")
    }

    override fun getListingInfo(dealType: Int, listingType: Int, propertyType: Int): List<String>? {
        return config.value?.listingStructures?.find { top ->
            top.deal_type == dealType &&
                    top.listing_type == listingType &&
                    top.property_type == propertyType
        }?.info?.split(",")
    }
}

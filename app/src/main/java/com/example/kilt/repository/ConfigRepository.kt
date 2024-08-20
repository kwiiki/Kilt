package com.example.kilt.repository

import com.example.kilt.data.Config

interface ConfigRepository {
    suspend fun getConfig():Config
    suspend fun getListingProps():List<String>

}
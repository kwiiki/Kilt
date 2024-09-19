package com.example.kilt.repository

import com.example.kilt.data.Config
import com.example.myapplication.data.HomeSale

interface HomeSaleRepository {
    suspend fun fetchHomeSale(id: String): HomeSale
//    suspend fun fetchConfig(): Config
}
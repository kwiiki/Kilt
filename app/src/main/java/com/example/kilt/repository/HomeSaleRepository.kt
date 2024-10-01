package com.example.kilt.repository

import com.example.kilt.data.HomeSale

interface HomeSaleRepository {
    suspend fun fetchHomeSale(id: String): HomeSale
}
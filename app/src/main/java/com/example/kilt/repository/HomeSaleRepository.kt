package com.example.kilt.repository

import com.example.kilt.models.HomeSale

interface HomeSaleRepository {
    suspend fun fetchHomeSale(id: String): HomeSale
}
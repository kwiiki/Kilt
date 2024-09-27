package com.example.kilt.repository

import com.example.kilt.data.kato.KatoResponse
import com.example.kilt.data.kato.MicroDistrictResponse
import com.example.kilt.network.ApiService

class KatoRepositoryImpl(private val apiService: ApiService):KatoRepository {
    override suspend fun getKatoById(id: String): KatoResponse {
        return apiService.getKato(id)
    }

    override suspend fun getMicroDistrict(id: String): MicroDistrictResponse {
        return apiService.getMicroDistrict(id)
    }
}
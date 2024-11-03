package com.example.kilt.repository

import com.example.kilt.models.kato.KatoResponse
import com.example.kilt.models.kato.MicroDistrictResponse
import com.example.kilt.models.kato.ResidentialComplexResponse
import com.example.kilt.network.ApiService

class KatoRepositoryImpl(private val apiService: ApiService):KatoRepository {
    override suspend fun getKatoById(id: String): KatoResponse {
        return apiService.getKato(id)
    }

    override suspend fun getMicroDistrict(id: String): MicroDistrictResponse {
        return apiService.getMicroDistrict(id)
    }

    override suspend fun getResidentialComplex(city: String): ResidentialComplexResponse {
        return apiService.getResidentialComplex(city)
    }
}
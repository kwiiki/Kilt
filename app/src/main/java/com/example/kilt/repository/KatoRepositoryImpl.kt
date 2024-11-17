package com.example.kilt.repository

import com.example.kilt.domain.choosecity.model.MicroDistrict
import com.example.kilt.models.kato.KatoResponse
import com.example.kilt.models.kato.ResidentialComplexResponse
import com.example.kilt.network.ApiService

class KatoRepositoryImpl(private val apiService: ApiService):KatoRepository {
    override suspend fun getKatoById(id: String): KatoResponse {
        return apiService.getKato(id)
    }

    override suspend fun getMicroDistrict(id: String): List<MicroDistrict> {
        val response = apiService.getMicroDistrict(id) // Returns MicroDistrictResponse

        // Map each MicroDistrictData to MicroDistrict with `isEnabled` defaulted to false
        return response.list.map { data ->
            MicroDistrict(
                id = data.id,
                parent_id = data.parent_id,
                name = data.name,
                isEnabled = false
            )
        }
    }

    override suspend fun getResidentialComplex(city: String): ResidentialComplexResponse {
        return apiService.getResidentialComplex(city)
    }
}
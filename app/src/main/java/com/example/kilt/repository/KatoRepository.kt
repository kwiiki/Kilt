package com.example.kilt.repository

import com.example.kilt.models.kato.KatoResponse
import com.example.kilt.models.kato.MicroDistrictResponse
import com.example.kilt.models.kato.ResidentialComplexResponse

interface KatoRepository {
   suspend fun getKatoById(id:String):KatoResponse
   suspend fun getMicroDistrict(id:String):MicroDistrictResponse
   suspend fun getResidentialComplex(city:String):ResidentialComplexResponse
}
package com.example.kilt.domain.choosecity.repository

import com.example.kilt.domain.choosecity.model.MicroDistrict
import com.example.kilt.models.kato.KatoResponse
import com.example.kilt.models.kato.ResidentialComplexResponse

interface KatoRepository {
   suspend fun getKatoById(id:String):KatoResponse
   suspend fun getMicroDistrict(id:String):List<MicroDistrict>
   suspend fun getResidentialComplex(city:String):ResidentialComplexResponse
}
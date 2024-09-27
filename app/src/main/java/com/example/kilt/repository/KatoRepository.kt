package com.example.kilt.repository

import com.example.kilt.data.kato.KatoResponse
import com.example.kilt.data.kato.MicroDistrictResponse

interface KatoRepository {
   suspend fun getKatoById(id:String):KatoResponse
   suspend fun getMicroDistrict(id:String):MicroDistrictResponse
}
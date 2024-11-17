package com.example.kilt.domain.choosecity.usecase

import com.example.kilt.domain.choosecity.model.MicroDistrict
import com.example.kilt.repository.KatoRepository

class GetMicroDistrictByIdUseCase(private val katoRepository: KatoRepository) {
    suspend fun execute(id:String): List<MicroDistrict> {
        return katoRepository.getMicroDistrict(id)
    }
}
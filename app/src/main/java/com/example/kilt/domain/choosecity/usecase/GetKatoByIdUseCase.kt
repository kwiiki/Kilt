package com.example.kilt.domain.choosecity.usecase

import com.example.kilt.models.kato.KatoResponse
import com.example.kilt.domain.choosecity.repository.KatoRepository

class GetKatoByIdUseCase(private val katoRepository: KatoRepository) {
    suspend fun execute(id:String): KatoResponse {
        return katoRepository.getKatoById(id = id)
    }
}
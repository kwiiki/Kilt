package com.example.kilt.domain.editprofile.usecase

import com.example.kilt.data.editprofile.dto.Filters
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.editprofile.repository.EditProfileRepository

class GetUserPhoneNumbersUseCase(private val repository: EditProfileRepository,
                                private val getUserIdUseCase: GetUserIdUseCase) {
    suspend operator fun invoke(): List<String> {
        val userPhoneNumbers = repository.findUserPhoneNumber(Filters(getUserIdUseCase.execute().toString()))
        return userPhoneNumbers.list.mapNotNull { it.phone.takeIf { it.isNotBlank() } }
    }
}
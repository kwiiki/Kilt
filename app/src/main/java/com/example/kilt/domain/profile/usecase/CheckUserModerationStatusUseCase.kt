package com.example.kilt.domain.profile.usecase

import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.profile.model.Result
import com.example.kilt.domain.profile.repository.ProfileRepository
import javax.inject.Inject

class CheckUserModerationStatusUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend fun execute(): Result {
        return repository.checkUserModerationStatus(getUserUseCase.execute()?.token ?: " ")
    }
}
package com.example.kilt.domain.editprofile.usecase

import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {
    suspend fun invoke(token: String): Response<Any> {
        return repository.deleteImage(token)
    }
}

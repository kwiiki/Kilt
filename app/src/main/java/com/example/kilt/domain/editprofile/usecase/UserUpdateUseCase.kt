package com.example.kilt.domain.editprofile.usecase

import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.editprofile.model.UpdatedUser
import com.example.kilt.domain.editprofile.repository.EditProfileRepository

class UserUpdateUseCase(private val repository: EditProfileRepository,
    private val getUserIdUseCase: GetUserIdUseCase) {
    suspend operator fun invoke(user: UpdatedUser) {
        return repository.updateUser(id = getUserIdUseCase.execute().toString(), user = user)
    }
}
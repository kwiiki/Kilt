package com.example.kilt.domain.editprofile.usecase

import android.util.Log
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import javax.inject.Inject

class ChangeUserTypeUseCase @Inject constructor(
    private val repository: EditProfileRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend fun invoke() {
        Log.d("checkToken", "invoke: ${getUserUseCase.execute()?.token.toString()}")
        return repository.changeUserType(getUserUseCase.execute()?.token ?: " ")
    }
}

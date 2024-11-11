package com.example.kilt.domain.editprofile.usecase

import com.example.kilt.domain.editprofile.repository.EditProfileRepository
import okhttp3.MultipartBody
import retrofit2.Response

class AddNewImageUseCase(private val repository: EditProfileRepository) {
    suspend operator fun invoke(image: MultipartBody.Part, token:String): Response<Any> {
        return repository.addNewImage(image = image, token = token)
    }
}
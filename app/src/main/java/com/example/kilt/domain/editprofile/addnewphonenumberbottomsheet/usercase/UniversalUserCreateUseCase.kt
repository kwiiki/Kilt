package com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Create
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import javax.inject.Inject

class UniversalUserCreateUseCase @Inject constructor(private val repository: AddNewPhoneNumberRepository) {
    suspend operator fun invoke(create: Create): Result<Any> {
        return repository.universalUserCreate(create)
    }
}
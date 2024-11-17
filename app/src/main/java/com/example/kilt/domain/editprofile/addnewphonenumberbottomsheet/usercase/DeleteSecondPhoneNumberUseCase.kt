package com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import javax.inject.Inject

class DeleteSecondPhoneNumberUseCase @Inject constructor(private val repository: AddNewPhoneNumberRepository) {
    suspend operator fun invoke(phone: Phone, token: String): Result<Unit> {
        return repository.removePhone(phone, token)
    }
}
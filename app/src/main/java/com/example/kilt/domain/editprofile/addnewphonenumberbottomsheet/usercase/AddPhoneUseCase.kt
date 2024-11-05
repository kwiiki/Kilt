package com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.AddPhoneDTO
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import javax.inject.Inject

class AddPhoneUseCase @Inject constructor(private val repository: AddNewPhoneNumberRepository) {
    suspend operator fun invoke(phone: Phone): Result<AddPhoneDTO> {
        return repository.addPhone(phone)
    }
}
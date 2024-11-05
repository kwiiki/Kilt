package com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.usercase

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.UserFindByOTPResult
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Filters
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import javax.inject.Inject

class UserFindByOTPUseCase @Inject constructor(private val repository: AddNewPhoneNumberRepository) {
    suspend operator fun invoke(filters: Filters): Result<UserFindByOTPResult> {
        return repository.userFindByOTP(filters)
    }
}
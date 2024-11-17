package com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.AddPhoneDTO
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.UserFindByOTPResult
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Create
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Filters
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone

interface AddNewPhoneNumberRepository {
    suspend fun addPhone(phone: Phone): Result<AddPhoneDTO>
    suspend fun userFindByOTP(filters: Filters): Result<UserFindByOTPResult>
    suspend fun universalUserCreate(create: Create) :Result<Any>
    suspend fun removePhone(phone: Phone, token: String): Result<Unit>

}
package com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.repository

import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.AddPhoneDTO
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.UserFindByOTPResult
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Create
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Filters
import com.example.kilt.data.editprofile.addnewphonenumberbottomsheet.dto.Phone
import com.example.kilt.domain.editprofile.addnewphonenumberbottomsheet.repository.AddNewPhoneNumberRepository
import com.example.kilt.network.ApiService

class AddNewPhoneNumberRepositoryImpl(private val apiService: ApiService): AddNewPhoneNumberRepository {
    override suspend fun addPhone(phone: Phone): Result<AddPhoneDTO> {
        return try {
            val response = apiService.addPhone(phone)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun userFindByOTP(filters: Filters): Result<UserFindByOTPResult> {
        return try {
            val response = apiService.userFindByOTP(filters)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun universalUserCreate(create: Create): Result<Any> {
        return try {
            val response = apiService.universalUserPhoneCreate(create)
            Result.success(response)
        } catch (e:Exception){
            Result.failure(e)
        }
    }
}
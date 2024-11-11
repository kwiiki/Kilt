package com.example.kilt.domain.userabout.usecase

import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.userabout.model.ListingList
import com.example.kilt.domain.userabout.repository.UserAboutRepository
import javax.inject.Inject

class GetUserListingsUseCase @Inject constructor(
    private val repository: UserAboutRepository,
    private val getUserIdUseCase: GetUserIdUseCase
) {
    suspend operator fun invoke(): ListingList {
        return repository.getUserListings(id = getUserIdUseCase.execute().toString())
    }
}
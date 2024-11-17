package com.example.kilt.domain.common

import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.models.authentification.User
import com.example.kilt.models.authentification.UserWithMetadata
import kotlinx.coroutines.flow.firstOrNull

class GetUserUseCase(private val userDataStoreManager: UserDataStoreManager) {
    suspend fun execute(): UserWithMetadata? {
        return userDataStoreManager.userDataFlow.firstOrNull()
    }
}

package com.example.kilt.domain.common

import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import kotlinx.coroutines.flow.firstOrNull

class GetUserIdUseCase(private val userDataStoreManager: UserDataStoreManager) {
    suspend fun execute(): Int {
        val userWithMetadata = userDataStoreManager.userDataFlow.firstOrNull()
        return userWithMetadata?.user?.id ?: -1
    }
}
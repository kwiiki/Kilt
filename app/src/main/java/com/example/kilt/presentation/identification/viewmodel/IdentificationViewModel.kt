package com.example.kilt.presentation.identification.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.models.authentification.UserWithMetadata
import com.example.kilt.data.localstorage.dataStore.UserDataStoreManager
import com.example.kilt.domain.config.repository.ConfigRepository
import com.example.kilt.domain.identification.IdentificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import android.util.Base64
import com.example.kilt.models.authentification.UserUpdateResult
import com.example.kilt.data.localstorage.sharedPreference.PreferencesHelper
import com.example.kilt.domain.editprofile.model.UpdatedUser
import com.example.kilt.utills.enums.IdentificationTypes

@HiltViewModel
class IdentificationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val identificationRepository: IdentificationRepository,
    private val configRepository: ConfigRepository,
    private val userDataStoreManager: UserDataStoreManager,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {
    val user: Flow<UserWithMetadata?> = userDataStoreManager.userDataFlow
    private val _userId = mutableStateOf<Int?>(null)

    private val _agencyItems = mutableStateOf<String?>(null)
    val agencyItems: State<String?> = _agencyItems

    private var _isUpdateSuccess = false
    private var _isUploadSuccess = false

    init {
        viewModelScope.launch {
            user.collect { userWithMetadata ->
                _userId.value = userWithMetadata?.user?.id
            }
        }

        viewModelScope.launch {
            configRepository.config.collect { config ->
                _agencyItems.value = config?.advanced?.agency_verification_items
            }
        }
    }

    fun uploadImages(
        selectedImagesMap: Map<String, List<Uri>>,
        agencyItems: List<String>,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            val parts = prepareImageParts(selectedImagesMap)
            try {
                when (val response = identificationRepository.uploadImages(parts)) {
                    is UserUpdateResult.UpdateUserSuccess -> {
                        _isUploadSuccess = true
                        checkBothRequestsSuccess()
                        onSuccess(response.success.toString())
                    }
                    is UserUpdateResult.UpdateUserFailure -> {
                        onError(Exception(response.message))
                    }
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
    private fun prepareImageParts(
        verificationDocuments: Map<String, List<Uri>>,
    ): List<MultipartBody.Part> {
        val imageParts = mutableListOf<MultipartBody.Part>()
        for ((verificationKey, uris) in verificationDocuments) {
            uris.forEachIndexed { index, uri ->
                val encodedKey = Base64.encodeToString(
                    verificationKey.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
                val fileName = "[$encodedKey]---${index+1}---${_userId.value}"
                val requestBody = getImageRequestBody(uri)
                if (requestBody != null) {
                    val part = MultipartBody.Part.createFormData(
                        name = "verification-images",
                        filename = fileName,
                        body = requestBody
                    )
                    imageParts.add(part)
                }
            }
        }
        return imageParts
    }
    private fun getImageRequestBody(uri: Uri): RequestBody? {
        return context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val bytes = inputStream.readBytes()
            RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
        }
    }
    fun updateUser(
        agentAbout: String,
        agentCity: String,
        agentFullAddress: String,
        agentWorkingHours: String,
        onSuccess: (UserUpdateResult) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val user = UpdatedUser(
                    agent_about = agentAbout,
                    agent_city = agentCity,
                    agent_full_address = agentFullAddress,
                    agent_working_hours = agentWorkingHours
                )
                val updatedUser = identificationRepository.identifyUser(
                    id = _userId.value.toString(), user = user)

                when (updatedUser) {
                    is UserUpdateResult.UpdateUserSuccess -> {
                        _isUpdateSuccess = true
                        checkBothRequestsSuccess()
                        onSuccess(updatedUser)
                    }
                    is UserUpdateResult.UpdateUserFailure -> {
                        onError(Exception(updatedUser.message))
                    }
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
    private fun checkBothRequestsSuccess() {
        if (_isUpdateSuccess && _isUploadSuccess) {
            preferencesHelper.setUserIdentificationStatus(IdentificationTypes.IsIdentified)
        }
    }
}
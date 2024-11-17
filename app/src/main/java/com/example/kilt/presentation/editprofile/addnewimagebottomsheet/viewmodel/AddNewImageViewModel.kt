package com.example.kilt.presentation.editprofile.addnewimagebottomsheet.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.common.GetUserUseCase
import com.example.kilt.domain.editprofile.usecase.AddNewImageUseCase
import com.example.kilt.domain.editprofile.usecase.DeleteImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@HiltViewModel
class AddNewImageViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val addNewImageUseCase: AddNewImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase
) : ViewModel() {

    fun uploadProfileImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            val user = getUserUseCase.execute()
            val token = user?.token

            val file = uriToFile(uri, context)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("profile-picture", file.name, requestFile)

            try {
                val response = addNewImageUseCase.invoke(
                    image = body,
                    token = "Bearer $token"
                )
                if (response.isSuccessful) {
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image.jpg")
        inputStream.use { input ->
            file.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        return file
    }
    fun deleteProfileImage() {
        viewModelScope.launch {
            val user = getUserUseCase.execute()
            val token = user?.token
            try {
                val response = deleteImageUseCase.invoke(token = "Bearer $token")
                if (response.isSuccessful) {
                    Log.d("DeleteImage", "Image deleted successfully")
                } else {
                    Log.d("DeleteImage", "Failed to delete image: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DeleteImage", "Error deleting image", e)
            }
        }
    }
}
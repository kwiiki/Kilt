package com.example.kilt.viewmodels

import androidx.lifecycle.ViewModel
import com.example.kilt.repository.EditProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(private val editProfileRepository: EditProfileRepository):ViewModel() {

}
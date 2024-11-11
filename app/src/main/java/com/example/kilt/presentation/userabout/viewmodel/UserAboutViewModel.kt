package com.example.kilt.presentation.userabout.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.domain.common.GetUserIdUseCase
import com.example.kilt.domain.userabout.model.ListingList
import com.example.kilt.domain.userabout.usecase.GetUserListingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAboutViewModel @Inject constructor(
    private val getUserListingsUseCase: GetUserListingsUseCase,
) : ViewModel() {

    private val _userListings = mutableStateOf(ListingList(emptyList()))
    val userListings: State<ListingList> = _userListings

    fun fetchUserListings() {
        viewModelScope.launch {
            try {
                val listings = getUserListingsUseCase()
                Log.d("listing", "Fetched listings: $listings")
                _userListings.value = listings
                Log.d("listing", "State updated with listings: ${_userListings.value}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
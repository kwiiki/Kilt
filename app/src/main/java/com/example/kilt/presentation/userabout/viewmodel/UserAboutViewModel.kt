package com.example.kilt.presentation.userabout.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _filteredListings = mutableStateOf(ListingList(emptyList()))
    val filteredListings: State<ListingList> = _filteredListings

    private val _countOfDealType1 = mutableIntStateOf(0)
    val countOfDealType1: State<Int> = _countOfDealType1

    private val _countOfDealType2 = mutableIntStateOf(0)
    val countOfDealType2: State<Int> = _countOfDealType2

    var selectedDealType by mutableStateOf<Int>(1)
    var selectedPropertyType by mutableStateOf<Int>(1)


    fun fetchUserListings() {
        viewModelScope.launch {
            try {
                val listings = getUserListingsUseCase()
                _userListings.value = listings
                _countOfDealType1.intValue = _userListings.value.listing.count { it.dealType == 1 }
                _countOfDealType2.intValue = _userListings.value.listing.count { it.dealType == 2 }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateFilter(dealType: Int, propertyType: Int) {
        selectedDealType = dealType
        selectedPropertyType = propertyType
        applyFilter()
    }

    private fun applyFilter() {
        val listings = _userListings.value.listing
        _filteredListings.value = ListingList(
            listings.filter {
                (it.dealType == selectedDealType) &&
                        (it.propertyType == selectedPropertyType)
            }
        )

    }
}
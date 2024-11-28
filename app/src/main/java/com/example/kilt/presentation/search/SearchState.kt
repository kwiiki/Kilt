package com.example.kilt.presentation.search

import com.example.kilt.models.PropertyItem

sealed class SearchState {
    data object Loading : SearchState()
    data class Error(val message: String) : SearchState()
    data object Empty : SearchState()
    data class Results(val properties: List<PropertyItem>) : SearchState()
}
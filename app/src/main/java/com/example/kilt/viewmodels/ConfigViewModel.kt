package com.example.kilt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilt.data.Config
import com.example.kilt.repository.ConfigRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(private val configRepository: ConfigRepositoryImpl) :
    ViewModel() {


    private val _config = MutableStateFlow<Config?>(null)
    val config: StateFlow<Config?> = _config

    private val _listingProps = MutableStateFlow<List<String>?>(null)
    val listingProps: StateFlow<List<String>?> = _listingProps

    fun loadHomeSale() {
        viewModelScope.launch {
            _config.value = configRepository.getConfig()
            _listingProps.value = configRepository.getListingProps()
        }
    }

    fun getFilterOptions(prop: String): List<Any>? {
        return when (prop) {
            "floor" -> config.value?.propMapping?.floor?.list
            "num_rooms" -> config.value?.propMapping?.num_rooms?.list
            // Добавьте остальные свойства здесь
            else -> null
        }
    }
}
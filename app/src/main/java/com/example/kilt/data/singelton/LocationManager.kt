package com.example.kilt.data.singelton

import android.util.Log

object LocationManager {
    private var selectedCity:String? = null
    private var selectedDistrict:String? = null
    private var selectedMicroDistrict:String? = null

    fun getSelectedDistrict(): String? = selectedDistrict

    fun setSelectedDistrict(district: String) {
        selectedDistrict = district
        Log.d("selectedMicroDistrict1", "setSelectedMicroDistrict: $selectedDistrict")
    }

    fun getSelectedMicroDistrict(): String? = selectedMicroDistrict

    fun setSelectedMicroDistrict(microDistrict: String) {
        selectedMicroDistrict = microDistrict
        Log.d("selectedMicroDistrict2", "setSelectedMicroDistrict: $selectedMicroDistrict")
    }

    fun getSelectedCity(): String? = selectedCity

    fun setSelectedCity(city: String) {
        selectedCity = city
        Log.d("selectedMicroDistrict3", "setSelectedMicroDistrict: $selectedCity")
    }
}
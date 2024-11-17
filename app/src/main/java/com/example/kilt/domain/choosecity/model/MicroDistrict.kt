package com.example.kilt.domain.choosecity.model

data class MicroDistrict(
    val id: String,
    val parent_id: String,
    val name: String,
    var isEnabled: Boolean = false,
    var isSelected:Boolean = true// Field to track selection state
)

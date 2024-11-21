package com.example.kilt.utills.enums

enum class IdentificationTypes(val value: Int) {
    IsIdentified(0),
    NotIdentified(1),
    Identified(2);

    companion object {
        fun fromInt(value: Int): IdentificationTypes {
            return entries.find { it.value == value } ?: NotIdentified
        }
    }
}
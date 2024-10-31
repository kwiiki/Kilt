package com.example.kilt.enums

enum class IdentificationTypes(val value: Int) {
    NotIdentified(0),
    IsIdentified(1),
    Identified(2);

    companion object {
        fun fromInt(value: Int): IdentificationTypes {
            return entries.find { it.value == value } ?: NotIdentified
        }
    }
}
package com.example.kilt.models.config

sealed class FilterItem {
    abstract val id: Int
    abstract val name: String
}
package com.example.kilt.data.config

sealed class FilterItem {
    abstract val id: Int
    abstract val name: String
}
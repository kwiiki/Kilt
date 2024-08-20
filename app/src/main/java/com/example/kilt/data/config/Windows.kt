package com.example.kilt.data.config

data class Windows(
    val list: List<WindowsItem>
)

data class WindowsItem(
    val id: Int,
    val name: String
)
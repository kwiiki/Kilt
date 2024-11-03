package com.example.kilt.models

data class Story(
    val id: Int,
    val userImage: String,
    val username: String,
    val storyImage: String,
    val viewed: Boolean = false
)

package com.example.kilt.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.kilt.data.Story

class StoryViewModel : ViewModel() {
    private val _stories = mutableStateListOf<Story>()
    val stories: List<Story> = _stories

    init {
        _stories.addAll(
            listOf(
                Story(1, "https://assets.dummyjson.com/public/qr-code.png", "user1", "https://assets.dummyjson.com/public/qr-code.png"),
                Story(2, "https://assets.dummyjson.com/public/qr-code.png", "user2", "https://assets.dummyjson.com/public/qr-code.png"),
            )
        )
    }

    fun markAsViewed(storyId: Int) {
        val index = _stories.indexOfFirst { it.id == storyId }
        if (index != -1) {
            _stories[index] = _stories[index].copy(viewed = true)
        }
    }
}
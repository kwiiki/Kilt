package com.example.kilt.data.shardePrefernce

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.edit

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val VIEWED_STORIES_KEY = "viewed_stories"
    }

    fun getViewedStories(count: Int): List<Boolean> {
        val viewedStories = sharedPreferences.getString(VIEWED_STORIES_KEY, null)
        return viewedStories?.split(",")?.map { it.toBoolean() } ?: List(count) { false }
    }

    fun setViewedStories(viewedStories: List<Boolean>) {
        val viewedStoriesString = viewedStories.joinToString(",")
        sharedPreferences.edit {
            putString(VIEWED_STORIES_KEY, viewedStoriesString)
        }
    }
}
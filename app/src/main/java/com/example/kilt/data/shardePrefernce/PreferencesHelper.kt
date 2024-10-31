package com.example.kilt.data.shardePrefernce

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.kilt.enums.IdentificationTypes

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val VIEWED_STORIES_KEY = "viewed_stories"
        private const val USER_AUTHENTICATED_KEY = "user_authenticated"
        private const val USER_IDENTIFIED_KEY = "user_identified_key"


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
    fun isUserAuthenticated(): Boolean {
        return sharedPreferences.getBoolean(USER_AUTHENTICATED_KEY, false)
    }

    fun setUserAuthenticated(isAuthenticated: Boolean) {
        sharedPreferences.edit {
            putBoolean(USER_AUTHENTICATED_KEY, isAuthenticated)
        }
    }
    fun getUserIdentificationStatus(): IdentificationTypes {
        val statusValue = sharedPreferences.getInt(USER_IDENTIFIED_KEY, IdentificationTypes.NotIdentified.value)
        return IdentificationTypes.fromInt(statusValue)
    }

    fun setUserIdentificationStatus(status: IdentificationTypes) {
        sharedPreferences.edit {
            putInt(USER_IDENTIFIED_KEY, status.value)
        }
    }

}
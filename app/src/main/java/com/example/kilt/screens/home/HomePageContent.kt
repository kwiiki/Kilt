package com.example.kilt.screens.home

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.kilt.data.shardePrefernce.PreferencesHelper
import com.example.kilt.viewmodels.SearchViewModel


@Composable
fun HomePageContent(navController: NavHostController,searchViewModel: SearchViewModel) {
    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context) }
    val images = remember {
        listOf(
            "https://cdn.dummyjson.com/products/images/beauty/Eyeshadow%20Palette%20with%20Mirror/1.png",
            "https://cdn.dummyjson.com/products/images/beauty/Powder%20Canister/thumbnail.png",
            "https://cdn.dummyjson.com/products/images/beauty/Powder%20Canister/1.png",
            "https://cdn.dummyjson.com/products/images/beauty/Eyeshadow%20Palette%20with%20Mirror/1.png"
        )
    }
    val viewedStories = remember {
        mutableStateListOf<Boolean>().apply {
            addAll(
                preferencesHelper.getViewedStories(images.size)
            )
        }
    }
    var selectedStory by remember { mutableStateOf<Int?>(null) }

    if (selectedStory == null) {
        HomePage(
            scrollState = rememberScrollState(),
            images = images,
            viewedStories = viewedStories,
            onStorySelected = { index -> selectedStory = index },
            navController,
            searchViewModel = searchViewModel
        )
    } else {
        InstagramStory(
            images = images,
            currentStoryIndex = selectedStory!!,
            onClose = {
                handleStoryClose(selectedStory!!, viewedStories, preferencesHelper)
                selectedStory = null
            },
            onStoryComplete = { index ->
                handleStoryComplete(
                    index,
                    viewedStories,
                    preferencesHelper
                )
            }
        )
    }
}

fun handleStoryClose(
    currentIndex: Int,
    viewedStories: MutableList<Boolean>,
    preferencesHelper: PreferencesHelper
) {
    for (i in 0..currentIndex) {
        viewedStories[i] = true
    }
    preferencesHelper.setViewedStories(viewedStories)
}

fun handleStoryComplete(
    index: Int,
    viewedStories: MutableList<Boolean>,
    preferencesHelper: PreferencesHelper
) {
    viewedStories[index] = true
    preferencesHelper.setViewedStories(viewedStories)
}


@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
}
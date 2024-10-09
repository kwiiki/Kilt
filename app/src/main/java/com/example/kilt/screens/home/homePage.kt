package com.example.kilt.screens.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun HomePage(
    scrollState: ScrollState,
    images: List<String>,
    viewedStories: List<Boolean>,
    onStorySelected: (Int) -> Unit,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        TopBar()
        StoryPreviews(stories = images, viewedStories = viewedStories, onStoryClick = onStorySelected)
        ButtonRow(searchViewModel = searchViewModel,Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))
        FindButton(text = "Поиск", searchViewModel = searchViewModel,navController,Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(24.dp))
        LazyRowForBlog(Modifier.padding(horizontal = 8.dp),navController)
        Spacer(modifier = Modifier.height(16.dp))
        AddAnnouncementButton(Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
    }
}


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

@Composable
fun HomePageContent(
    scrollState: ScrollState,
    images: List<String>,
    viewedStories: List<Boolean>,
    onStorySelected: (Int) -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(30.dp)
//                .background(Color.Black)
//        )
        TopBar()
        StoryPreviews(stories = images, viewedStories = viewedStories, onStoryClick = onStorySelected)
        Body(Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))
        FindButton(Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        LazyRowForBlog(Modifier.padding(horizontal = 8.dp),navController)
        Spacer(modifier = Modifier.height(16.dp))
        AddAnnouncementButton(Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
    }
}


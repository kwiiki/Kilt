package com.example.kilt.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kilt.screens.searchpage.InstagramProgressIndicator
import kotlin.math.max
import kotlin.math.min

@Composable
fun Story(){
    }
@Composable
fun InstagramStory(
    images: List<String>,
    currentStoryIndex: Int,
    onClose: () -> Unit,
    onStoryComplete: (Int) -> Unit
) {
    val stepCount = images.size
    val currentStep = remember { mutableStateOf(currentStoryIndex) }
    val isPaused = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val imageModifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        currentStep.value = if (offset.x < size.width / 2) {
                            max(0, currentStep.value - 1)
                        } else {
                            min(stepCount - 1, currentStep.value + 1)
                        }
                        isPaused.value = false
                    },
                    onPress = {
                        isPaused.value = true
                        awaitRelease()
                        isPaused.value = false
                    }
                )
            }

        // Display the current image
        AsyncImage(
            model = images[currentStep.value],
            contentDescription = "Story Image",
            contentScale = ContentScale.Fit,
            modifier = imageModifier
        )

        // Progress indicator
        InstagramProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            stepCount = stepCount,
            stepDuration = 4_000,
            unSelectedColor = Color.LightGray,
            selectedColor = Color.White,
            currentStep = currentStep.value,
            onStepChanged = {
                currentStep.value = it
                onStoryComplete(it - 1)
            },
            isPaused = isPaused.value,
            onComplete = {
                onStoryComplete(stepCount - 1)
                onClose()
            }
        )

        // Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 36.dp)
                .padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Story",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)

            )
        }
    }
}



@Composable
fun StoryPreviews(stories: List<String>, viewedStories: List<Boolean>, onStoryClick: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(stories.size) { index ->
            val storyImage = stories[index]
            val borderColor = if (viewedStories[index]) Color.Gray else Color.Blue
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(102.dp)
                    .width(81.dp)
                    .clip(RectangleShape)
                    .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                    .clickable { onStoryClick(index) }
            ) {
                AsyncImage(
                    model = storyImage,
                    contentDescription = "Story preview",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}




    @Composable
@Preview(showBackground = true)
fun PreviewStory(){
    Story()
}
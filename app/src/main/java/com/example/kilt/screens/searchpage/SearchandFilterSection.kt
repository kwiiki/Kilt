@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.kilt.R
import com.example.kilt.screens.searchpage.filter.FilterPage
import com.example.kilt.screens.searchpage.filter.PriorityBottomSheet


@Composable
fun SearchAndFilterSection() {
    var openPriorityBottomSheet by remember { mutableStateOf(false) }
    var openFilterBottomSheet by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val filterBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchBar()
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.vec),
                contentDescription = "Значок электронной почты",
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .padding(8.dp)
                    .clickable { openPriorityBottomSheet = true }
            )
        }
        if (openPriorityBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                sheetState = bottomSheetState,
                onDismissRequest = { openPriorityBottomSheet = false },
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomSheetDefaults.DragHandle(color = Color(0xff010101))
                    }
                }
            ) {
                PriorityBottomSheet(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        openPriorityBottomSheet = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xffF2F2F2), shape = (RoundedCornerShape(16.dp)))
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(alignment = Alignment.Center)
                        .clickable { openFilterBottomSheet = true }
                )
            }
            FilterScreen()
        }
    }

    if (openFilterBottomSheet) {
        ModalBottomSheet(
            tonalElevation = 20.dp,
            shape = RectangleShape,
            sheetState = filterBottomSheetState,
            onDismissRequest = { openFilterBottomSheet = false },
            dragHandle = {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(30.dp)
//                        .background(Color.White)
//                ) {
//                    BottomSheetDefaults.DragHandle(
//                        modifier = Modifier
//                            .align(Alignment.Center),
//                        color = Color(0xff010101)
//                    )
//                }
            }
        ) {
            FilterPage()
        }
    }
    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.currentValue }
            .collect { state ->
                if (state != SheetValue.Expanded) {
                    bottomSheetState.expand()
                }
            }
    }
}


//@Composable
//@Preview(showBackground = true)
//fun PreviewSearchAndFilterSection() {
//    SearchAndFilterSection()
//}
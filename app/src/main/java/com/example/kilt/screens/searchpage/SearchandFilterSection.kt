@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage

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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.kilt.R
import com.example.kilt.screens.searchpage.filter.PriorityBottomSheet


@Composable
fun SearchAndFilterSection() {
    var openBottomSheet by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
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
                    .clickable { openBottomSheet = true }
            )
        }
        if (openBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                sheetState = bottomSheetState,
                onDismissRequest = { openBottomSheet = false },
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
                        openBottomSheet = false
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
                )
            }
            FilterScreen()
        }
    }
}


//@Composable
//@Preview(showBackground = true)
//fun PreviewSearchAndFilterSection() {
//    SearchAndFilterSection()
//}
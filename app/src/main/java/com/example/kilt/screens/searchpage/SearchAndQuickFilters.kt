@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.kilt.screens.searchpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.screens.searchpage.filter.FilterPage
import com.example.kilt.screens.searchpage.filter.PriorityBottomSheet
import com.example.kilt.screens.searchpage.quickFilters.QuickFilters
import com.example.kilt.viewmodels.ChooseCityViewModel
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel

@Composable
fun SearchAndQuickFilters(
    chooseCityViewModel: ChooseCityViewModel,
    navController: NavHostController,
    configViewModel: ConfigViewModel,
    searchViewModel: SearchViewModel,
    modifier: Modifier
) {
    var openPriorityBottomSheet by remember { mutableStateOf(false) }
    var openFilterBottomSheet by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val filterBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden }
    )
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchBar(chooseCityViewModel,navController,modifier = Modifier.fillMaxWidth(0.88f))
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.vec),
                contentDescription = "Значок электронной почты",
                modifier = Modifier
                    .size(20.dp, 20.dp)
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
                    searchViewModel = searchViewModel,
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        openPriorityBottomSheet = false
                    }
                )
            }
        }
        QuickFilters(
            configViewModel = configViewModel,
            searchViewModel = searchViewModel,
            onFilterButtonClicked = { openFilterBottomSheet = it })
    }
    if (openFilterBottomSheet) {
        ModalBottomSheet(
            tonalElevation = 20.dp,
            shape = RectangleShape,
            sheetState = filterBottomSheetState,
            onDismissRequest = { openFilterBottomSheet = false },
            dragHandle = {
                BottomSheetDefaults.DragHandle()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            openFilterBottomSheet = false
                        }, modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Close, contentDescription = "Close",
                            tint = Color(0xff566982),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        "Фильтр",
                        color = Color(0xff010101),
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 35.dp),
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        onClick = { searchViewModel.clearAllFilters()
                                    chooseCityViewModel.resetSelection()},
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            "Стереть",
                            color = Color(0xff566982),
                            fontWeight = FontWeight.W400,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        ) {
            FilterPage(
                chooseCityViewModel = chooseCityViewModel,
                navController,
                configViewModel,
                searchViewModel,
                onCloseFilterBottomSheet = { openFilterBottomSheet = false })
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
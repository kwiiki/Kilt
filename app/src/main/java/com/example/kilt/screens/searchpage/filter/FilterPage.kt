package com.example.kilt.screens.searchpage.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kilt.data.config.FilterItem
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.SearchViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterPage(configViewModel: ConfigViewModel,searchViewModel: SearchViewModel = hiltViewModel()) {
    FilterContent(configViewModel = configViewModel,searchViewModel)
//    Scaffold(
////        topBar = { FilterTopAppBar() },
//        content = { FilterContent(configViewModel) },
//    )
}

@Composable
fun FilterContent(configViewModel: ConfigViewModel, searchViewModel: SearchViewModel) {
    val config by configViewModel.config.collectAsState()
    val listingProps by configViewModel.listingProps.collectAsState()

    LaunchedEffect(Unit) {
        configViewModel.loadHomeSale()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        ButtonRow(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), configViewModel)
//        Divider()
        TypeOfHousing(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp), configViewModel = configViewModel, searchViewModel = searchViewModel)
        Divider()
        LocationSection(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        Divider()
        OnlyOwnersSection()
        Divider()
        listingProps?.forEach { prop ->
            val matchingLabel = config?.propLabels?.find { it.property == prop }
            when (matchingLabel?.filter_type) {
                "list" -> ListFilter(configViewModel, prop, matchingLabel.label_ru )
                "list-multiple" -> ListFilter(configViewModel, prop, matchingLabel.label_ru )
                "range" -> RangeFilter(prop, matchingLabel.label_ru )
            }
//            Divider()
        }
    }
}
@Composable
fun RangeFilter(prop: String, title: String) {
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }

    val trailingText = when (prop) {
        "price" -> "тг."
        "area", "living_area", "land_area", "kitchen_area" -> "м²"
        else -> ""
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = minPrice,
                onValueChange = { newValue ->
                    minPrice = newValue.filter { it.isDigit() }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    if (trailingText.isNotEmpty()) {
                        Text(
                            text = trailingText,
                            fontWeight = FontWeight.W400,
                            color = Color(0xff010101)
                        )
                    }
                }
            )
            Text(text = "до", modifier = Modifier.padding(horizontal = 8.dp))
            OutlinedTextField(
                value = maxPrice,
                onValueChange = { newValue ->
                    maxPrice = newValue.filter { it.isDigit() }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    if (trailingText.isNotEmpty()) {
                        Text(
                            text = trailingText,
                            fontWeight = FontWeight.W400,
                            color = Color(0xff010101)
                        )
                    }
                }
            )
        }
    }
    Divider()
}

@Composable
fun ListFilter(
    viewModel: ConfigViewModel,
    prop: String,
    title: String
) {
    val filters = viewModel.getFilterOptions(prop)

    FilterButtons(
        filters = filters,
        title = title,
        onFilterSelected = { selectedFilters ->
            println("chooseFilter: $selectedFilters")

        }
    )
}

@Composable
fun FilterButtons(
    filters: List<Any>?,
    title: String,
    onFilterSelected: (List<Int>) -> Unit
) {
    var selectedFilters by remember { mutableStateOf<List<Int>>(emptyList()) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterButton(
                    text = "Все",
                    isSelected = selectedFilters.isEmpty(),
                    onClick = {
                        selectedFilters = emptyList()
                        onFilterSelected(selectedFilters)
                    }
                )
            }
            items(filters ?: emptyList()) { filter ->
                when (filter) {
                    is FilterItem -> {
                        FilterButton(
                            text = filter.name,
                            isSelected = selectedFilters.contains(filter.id),
                            onClick = {
                                selectedFilters = if (filter.id in selectedFilters) {
                                    selectedFilters - filter.id
                                } else {
                                    selectedFilters + filter.id
                                }
                                onFilterSelected(selectedFilters)
                            }
                        )
                    }
                    else -> {
                        // Handle other types if necessary
                    }
                }
            }
        }
    }
    Divider()
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(42.dp)
            .widthIn(min = 47.dp)
            .border(
                width = 1.5.dp,
                color = if (isSelected) Color.Blue else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Color.Blue.copy(alpha = 0.1f) else Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .widthIn(37.dp),
            color = if (isSelected) Color.Blue else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun Divider() {
    Spacer(
        modifier = Modifier
            .height(1.5.dp)
            .fillMaxWidth()
            .background(Color(0xffe6e6e6))
    )
}
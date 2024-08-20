package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kilt.viewmodels.HomeSaleViewModel


@Preview(showBackground = true)
@Composable
fun PreviewFilterPage() {
    FilterPage()
}

@Composable
fun FilterPage() {
    val homeSaleViewModel: HomeSaleViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FilterTopAppBar()
        FilterContent(homeSaleViewModel)
    }
}

@Composable
fun FilterContent(homeSaleViewModel: HomeSaleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtonRow(Modifier.padding(16.dp))
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        TypeOfHousing(Modifier.padding(horizontal = 8.dp))
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        LocationSection(Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        OnlyOwnersSection()
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        ChooseNumRoom(homeSaleViewModel)
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
//        TypeOfConstruction(homeSaleViewModel)
        Spacer(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .background(Color(0xffDBDFE4))
        )
        FloorNum(homeSaleViewModel)

    }
}

//@Composable
//fun <T> TypeOfConstruction(
//    homeSaleViewModel: HomeSaleViewModel,
//    getFilters: (HomeSaleViewModel) -> List<T>?,
//    getId: (T) -> String,
//    getName: (T) -> String,
//    title: String // Добавляем параметр для заголовка
//) {
//    homeSaleViewModel.loadHomeSale()
//    val filters = getFilters(homeSaleViewModel)
//    Log.d("TypeOfConstruction", "TypeOfConstruction: ${filters?.size}")
//    TypeFilterButtons(
//        filters = filters,
//        getId = getId,
//        getName = getName,
//        title = title, // Передаем заголовок
//        onFilterSelected = { selectedFilters ->
//            println("Выбраны фильтры: $selectedFilters")
//        }
//    )
//}
//
//@Composable
//fun ConstructionTypeFilterButton(
//    text: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .height(40.dp)
//            .widthIn(35.dp)
//            .wrapContentWidth()
//            .border(
//                width = 1.5.dp,
//                color = if (isSelected) Color.Blue else Color(0xffc2c2d6),
//                shape = RoundedCornerShape(16.dp)
//            )
//            .clickable(onClick = onClick)
//            .background(
//                color = if (isSelected) Color.Blue.copy(alpha = 0.1f) else Color(0xffFFFFFF),
//                shape = RoundedCornerShape(16.dp)
//            )
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = text,
//            fontSize = 14.sp,
//            color = if (isSelected) Color.Blue else Color(0xff110D28),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier
//                .widthIn(35.dp)
//                .align(alignment = Alignment.Center),
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//fun <T> TypeFilterButtons(
//    filters: List<T>?,
//    getId: (T) -> String,
//    getName: (T) -> String,
//    title: String, // Добавляем параметр для заголовка
//    onFilterSelected: (List<String>) -> Unit
//) {
//    var selectedFilters by remember { mutableStateOf<List<String>>(emptyList()) }
//
//    Column(modifier = Modifier
//        .padding(horizontal = 16.dp)
//        .padding(top = 8.dp)) {
//        Text(
//            text = title, // Используем переданный заголовок
//            fontSize = 18.sp,
//            fontWeight = FontWeight.W700,
//            color = Color(0xff010101)
//        )
//    }
//
//    LazyRow(
//        modifier = Modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        item {
//            ConstructionTypeFilterButton(
//                text = "Все",
//                isSelected = selectedFilters.isEmpty(),
//                onClick = {
//                    selectedFilters = emptyList()
//                    onFilterSelected(selectedFilters)
//                }
//            )
//        }
//        items(filters ?: emptyList()) { filter ->
//            ConstructionTypeFilterButton(
//                text = getName(filter),
//                isSelected = selectedFilters.contains(getId(filter)),
//                onClick = {
//                    selectedFilters = if (selectedFilters.contains(getId(filter))) {
//                        selectedFilters - getId(filter)
//                    } else {
//                        selectedFilters + getId(filter)
//                    }
//                    onFilterSelected(selectedFilters)
//                }
//            )
//        }
//    }
//}



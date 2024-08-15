package com.example.kilt.screens.searchpage.homedetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.kilt.data.Config
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.myapplication.data.HomeSale

@Composable
fun InfoSection(homeSaleViewModel: HomeSaleViewModel) {
    val homeSale by homeSaleViewModel.homeSale
    homeSaleViewModel.loadHomesale()
    val config = homeSaleViewModel.config


    Log.d("designation", "InfoSection: ${config.value?.propMapping?.furniture?.list?.get(0)?.name}")
    Log.d("designation1", "InfoSection: ${homeSale?.listing?.property_type}")
    Log.d("designation2", "InfoSection: ${homeSale}")

    Log.d("listingType", "InfoSection: ${homeSale?.listing?.listing_type}")
    when (homeSale?.listing?.property_type) {
        1 -> {
            FlatInfoSection(homeSale!!, config)
        }
        2 -> {
            HomeInfoSection(homeSaleViewModel)
        }
        else -> {
            CommercialInfoSection(homeSale!!, config)
        }
    }
}

@Composable
fun CommercialInfoSection(homeSale: HomeSale?, config: MutableState<Config?>) {
    val configList = config.value?.propMapping?.designation?.list
    val locatedList = config.value?.propMapping?.where_located?.list
    val lineList = config.value?.propMapping?.line_of_houses?.list
    val designationIds = homeSale?.listing?.designation?.split(",")?.mapNotNull { it.toIntOrNull() }
    val matchingDesignations = designationIds?.mapNotNull { id ->
        configList?.find { it.id == id }
    }
    val matchingLocation =
        homeSale?.listing?.where_located?.let { locatedList?.find { it.id == homeSale.listing.where_located } }
    val matchingLineHouse =
        homeSale?.listing?.line_of_houses?.let { lineList?.find { it.id == homeSale.listing.line_of_houses } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val infoList = listOf(
            "Год постройки" to (homeSale?.listing?.built_year?.takeIf { it != 0 }?.toString()),
            "Площадь" to homeSale?.listing?.area?.toString(),
            "Высота потолков" to (homeSale?.listing?.ceiling_height?.takeIf { it != 0.0 }
                ?.toString()),
            "Назначение" to matchingDesignations?.joinToString(", ") { it.name }
                .takeIf { !it.isNullOrEmpty() },
            "Где размещён" to matchingLocation?.name,
            "Линия домов" to matchingLineHouse?.name
        )

        infoList.forEach { (label, value) ->
            if (value != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        modifier = Modifier.width(150.dp),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}


@Composable
fun HomeInfoSection(homeSaleViewModel: HomeSaleViewModel) {
    val homeSale by homeSaleViewModel.homeSale
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (column1, column2) = createRefs()

        Column(
            modifier = Modifier.constrainAs(column1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            DetailItemRight(label = "Тип недвижимости")
            DetailItemRight(label = "Количество комнат")
            DetailItemRight(label = "Площадь")
        }

        Column(
            modifier = Modifier.constrainAs(column2) {
                start.linkTo(column1.end, margin = 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            DetailItemLeft(value = "Дом")
            DetailItemLeft(value = homeSaleViewModel.homeSale.value?.listing?.num_rooms.toString())
            DetailItemLeft(value = "44 м²")
        }
    }
}

@Composable
fun FlatInfoSection(homeSale: HomeSale, config: MutableState<Config?>) {
    val furnitureList = config.value?.propMapping?.furniture?.list
    val matchingFurniture = furnitureList?.find { it.id == homeSale.listing.furniture }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        DetailItem("Тип недвижимости", "Квартира")
        DetailItem("Количество комнат", homeSale.listing.num_rooms.toString())
        DetailItem("Этаж", homeSale.listing.floor.toString())
        DetailItem("Мебилирована", matchingFurniture?.name)
        DetailItem("Площадь", homeSale.listing.area.let { "$it м²" })
    }
}

@Composable
fun DetailItem(label: String, value: String?) {
    if (value != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Visible
            )
        }
    }
}

@Composable
fun DetailItemRight(label: String? = null, value: String? = null) {
    Text(
        text = label ?: value.orEmpty(),
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun DetailItemLeft(label: String? = null, value: String? = null) {
    Text(
        text = label ?: value.orEmpty(),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(), // Ensure the Text fills the width available
        maxLines = Int.MAX_VALUE, // Allow for multiple lines if needed
        overflow = TextOverflow.Visible // Show text on multiple lines
    )
}
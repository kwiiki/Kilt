package com.example.kilt.screens.searchpage.homedetails

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kilt.data.Config
import com.example.kilt.viewmodels.ConfigViewModel
import com.example.kilt.viewmodels.HomeSaleViewModel
import com.example.myapplication.data.HomeSale

@Composable
fun InfoSection(homeSale: HomeSale?, homeSaleViewModel: HomeSaleViewModel,configViewModel: ConfigViewModel) {
    val homeSaleView by homeSaleViewModel.homeSale
    homeSaleViewModel.loadHomeSale()
    val config = homeSaleViewModel.config

    Log.d("comer", "InfoSection: ${homeSale?.listing?.property_type}")
    Log.d("comer", "InfoSection: $homeSale")
    when (homeSale?.listing?.property_type) {
        1 -> {
            if (config.value != null) {
                FlatInfoSection(homeSale, config.value!!,configViewModel)
            }
        }
        2 -> {
            if (config.value != null) {
                HomeInfoSection(homeSale, config.value!!)
            }
        }
        else -> {
            if (homeSale != null && config.value != null) {
                CommercialInfoSection(homeSale, config.value!!,homeSaleViewModel)
            }
        }
    }
}
@Composable
fun FlatInfoSection(homeSale: HomeSale, config: Config,configViewModel: ConfigViewModel) {
    val listingStructureInfo = configViewModel.listingInfo
    val propLabels = config.propLabels
    val furnitureList = config.propMapping.furniture.list
    val matchingFurniture = furnitureList.find { it.id == homeSale.listing.furniture }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Отображение информации о квартире
        DetailItem("Тип недвижимости", "Квартира")
        DetailItem("Количество комнат", homeSale.listing.num_rooms.toString())
        DetailItem("Этаж", homeSale.listing.floor.toString())
        DetailItem("Мебилирована", matchingFurniture?.name.toString())
        DetailItem("Площадь", homeSale.listing.area.let { "$it м²" })
    }
}
@Composable
fun CommercialInfoSection(homeSale: HomeSale?, config: Config,homeSaleViewModel: HomeSaleViewModel) {
    val configList = config.propMapping.designation.list
    val locatedList = config.propMapping.where_located.list
    val lineList = config.propMapping.line_of_houses.list


    val designationIds = homeSale?.listing?.designation?.split(",")?.mapNotNull { it.toIntOrNull() }
    val matchingDesignations = designationIds?.mapNotNull { id ->
        configList.find { it.id == id }
    }
    val matchingLocation =
        homeSale?.listing?.where_located?.let { locatedList.find { it.id == homeSale.listing.where_located } }
    val matchingLineHouse =
        homeSale?.listing?.line_of_houses?.let { lineList.find { it.id == homeSale.listing.line_of_houses } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val infoList = listOf(
            "Год постройки" to (homeSale?.listing?.built_year?.takeIf { it != 0 }?.toString()),
            "Площадь" to homeSale?.listing?.area?.toString(),
            "Высота потолков" to (homeSale?.listing?.ceiling_height?.takeIf { it != 0.0 }?.toString()),
            "Назначение" to matchingDesignations?.joinToString(", ") { it.name }.takeIf { !it.isNullOrEmpty() },
            "Где размещён" to matchingLocation?.name,
            "Линия домов" to matchingLineHouse?.name
        )

        infoList.forEach { (label, value) ->
            if (value != null) {
                DetailItem(label, value)
            }
        }
    }
}


@Composable
fun HomeInfoSection(homeSale: HomeSale, config: Config) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        DetailItem("Тип недвижимости", "Дом")
        DetailItem("Количество комнат", homeSale.listing.num_rooms.toString())
        DetailItem("Площадь", homeSale.listing.area.let { "$it м²" })
    }
}
@Composable
fun DetailItem(label: String, value: String) {
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


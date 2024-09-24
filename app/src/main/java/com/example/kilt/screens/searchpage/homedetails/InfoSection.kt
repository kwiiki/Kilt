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
    val propertyType = homeSale?.listing?.property_type

    Log.d("comer", "InfoSection: ${homeSale?.listing?.property_type}")
    Log.d("comer", "InfoSection: $homeSale")
    when (homeSale?.listing?.property_type) {
        1 -> {
            if (config.value != null) {
                FlatInfoSection(homeSale, config.value!!,configViewModel,propertyType)
            }
        }
        2 -> {
            if (config.value != null) {
                FlatInfoSection(homeSale, config.value!!,configViewModel,propertyType)
            }
        }
        else -> {
            if (homeSale != null && config.value != null) {
                FlatInfoSection(homeSale, config.value!!,configViewModel,propertyType)
            }
        }
    }
}
@Composable
fun FlatInfoSection(
    homeSale: HomeSale,
    config: Config,
    configViewModel: ConfigViewModel,
    propertyType: Int?
) {
    val listingStructureInfo = configViewModel.listingInfo
    val propLabels = config.propLabels

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Log.d("test", "FlatInfoSection: ${homeSale.listing.furniture}")
        Log.d("test", "FlatInfoSection: ${homeSale.listing.id}")
        DetailItem("Тип недвижимости", if (propertyType == 1) "Квартира" else "Дом")

        listingStructureInfo.value.forEach { propertyName ->
            val label = propLabels.find { it.property == propertyName }
            val value = when (propertyName) {
                "num_rooms" -> homeSale.listing.num_rooms.takeIf { it != null && it != 0 }?.toString()
                "floor" -> homeSale.listing.floor.takeIf { it != null && it != 0 }?.toString()
                "area" -> homeSale.listing.area.takeIf { it != null && it != 0.0 }?.let {
                    if (it.rem(1) == 0.0) "${it.toInt()} м²" else "$it м²"
                }
                "built_year" -> homeSale.listing.built_year.takeIf { it != null && it != 0 }?.toString()
                "num_floors" -> homeSale.listing.num_floors.takeIf { it != null && it != 0 }?.toString()
                "ceiling_height" -> homeSale.listing.ceiling_height.takeIf { it != null && it != 0.0 }?.let {
                    if (it.rem(1) == 0.0) "${it.toInt()} м" else "$it м"
                }
                "max_usage" -> homeSale.listing.max_usage.takeIf { it != null && it != "" }?.toString()
                else -> null
            }
            value?.let {
                DetailItem(label?.label_ru ?: propertyName, it)
            }
        }

        // Пример обработки мебели
        val furnitureList = config.propMapping.furniture.list
        val lineOfHouseList = config.propMapping.line_of_houses.list
        val whereLocatedList = config.propMapping.where_located.list
        val designationList = config.propMapping.designation.list

        val matchingFurniture = furnitureList.find { it.id == homeSale.listing.furniture }
        matchingFurniture?.let {
            DetailItem("Меблирована", it.name)
        }
        val matchingLineOfHouses = lineOfHouseList.find { it.id == homeSale.listing.line_of_houses }
        matchingLineOfHouses?.let {
            DetailItem(label = "Линия домов", value = it.name)
        }
        val matchingLocated = whereLocatedList.find { it.id == homeSale.listing.where_located }
        matchingLocated?.let {
            DetailItem(label = "Где размещён", value = it.name)
        }
        // Поиск и отображение значений назначения
        val designationIds = homeSale.listing.designation?.split(",")?.map { it.trim().toIntOrNull() }?.filterNotNull() ?: emptyList()
        val matchedDesignations = designationList.filter { it.id in designationIds }
        if (matchedDesignations.isNotEmpty()) {
            matchedDesignations.forEach { designation ->
                DetailItem(label = "Назначение", value = designation.name)
            }
        }
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


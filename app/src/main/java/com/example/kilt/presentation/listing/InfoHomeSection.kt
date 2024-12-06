package com.example.kilt.presentation.listing

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kilt.presentation.listing.viewmodel.ListingViewModel

@Composable
fun InfoHomeSection(listingViewModel: ListingViewModel) {
    val homeSale by listingViewModel.homeSale
    listingViewModel.loadHomeSale()
    Log.d("built_year", "InfoHomeSection: ${homeSale?.listing?.built_year}")

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (homeSale != null) {
            val builtYear = homeSale!!.listing.built_year
            if (builtYear != 0) {
                DetailItem("Год постройки", builtYear.toString())
            }
        }
    }
}
package com.example.kilt.presentation.search.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kilt.R

@Composable
fun CustomMarker(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = Color.Blue,
        )
    }
}

fun createBitmapFromXml(context: Context, price: String): Bitmap {
    val formattedPrice = mapPriceFormat(price)
    val view = LayoutInflater.from(context).inflate(R.layout.custom_marker_with_pointer, null)

    val textView = view.findViewById<TextView>(R.id.marker_text)
    textView.text = formattedPrice

    view.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)

    val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)

    return bitmap
}

fun mapPriceFormat(price: String): String {

    val cleanedPrice = price.replace("[^\\d.,]".toRegex(), "").replace(",", ".")
    val priceDouble = cleanedPrice.toDoubleOrNull() ?: return price

    return when {
        priceDouble >= 1_000_000_000 -> {
            if (priceDouble % 1_000_000_000 == 0.0) {
                "${(priceDouble / 1_000_000_000).toInt()} млрд"
            } else {
                "%.2f млрд".format(priceDouble / 1_000_000_000)
            }
        }
        priceDouble >= 1_000_000 -> {
            if (priceDouble % 1_000_000 == 0.0) {
                "${(priceDouble / 1_000_000).toInt()} млн"
            } else {
                "%.1f млн".format(priceDouble / 1_000_000)
            }
        }
        priceDouble >= 1_000 -> {
            if (priceDouble % 1_000 == 0.0) {
                "${(priceDouble / 1_000).toInt()} тыс"
            } else {
                "%.1f тыс".format(priceDouble / 1_000)
            }
        }
        else -> {
            if (priceDouble % 1 == 0.0) {
                "${priceDouble.toInt()}"
            } else {
                priceDouble.toString()
            }
        }
    }
}

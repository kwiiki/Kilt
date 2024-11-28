package com.example.kilt.presentation.search.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.kilt.R

fun createClusterBitmap(context: Context, objectCount: Int): Bitmap {
    val view = LayoutInflater.from(context).inflate(R.layout.custom_cluster, null)

    val textView = view.findViewById<TextView>(R.id.cluster_count)
    textView.text = objectCount.toString()

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

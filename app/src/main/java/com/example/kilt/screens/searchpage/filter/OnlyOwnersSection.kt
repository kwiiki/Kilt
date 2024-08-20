package com.example.kilt.screens.searchpage.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun OnlyOwnersSection() {
    var checked by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xff4747d1),
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White
            ),

            )

        Text(
            "Только от хозяев",
                 fontSize = 16.sp,
                 fontWeight = FontWeight.W400
        )

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewOnlyOwnersSection() {
    OnlyOwnersSection()
}
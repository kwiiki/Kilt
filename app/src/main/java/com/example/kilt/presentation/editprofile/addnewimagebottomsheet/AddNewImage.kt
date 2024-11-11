package com.example.kilt.presentation.editprofile.addnewimagebottomsheet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kilt.presentation.editprofile.addnewimagebottomsheet.viewmodel.AddNewImageViewModel
import com.example.kilt.presentation.editprofile.components.CustomButtonForEdit
import com.example.kilt.presentation.editprofile.gradientBrush
import com.example.kilt.presentation.editprofile.listColor


val redGradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFFE63312), Color(0xFFE63312))
)
val redListColor = listOf(Color(0xFFE63312), Color(0xFFE63312))
@Composable
fun AddNewImage(
    addNewImageViewModel: AddNewImageViewModel,
    onClick: () -> Unit,
    checkUserImage: () -> Unit
) {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            addNewImageViewModel.uploadProfileImage(uri, context)
            onClick()
        } else {
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .height(140.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        CustomButtonForEdit(
            text = "Выбрать фото",
            onClick = {
                galleryLauncher.launch("image/*")
            },
            colorList = listColor,
            colorBrush = gradientBrush
        )
        CustomButtonForEdit(
            text = "Удалить фото",
            onClick = {
                addNewImageViewModel.deleteProfileImage()
                checkUserImage()
                onClick()
            },
            colorList = redListColor,
            colorBrush = redGradientBrush
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

package com.example.kilt.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.kilt.R
import com.example.kilt.navigation.NavPath
import com.example.kilt.presentation.editprofile.CustomTextField
import com.example.kilt.presentation.editprofile.TextSectionTitle
import com.example.kilt.viewmodels.IdentificationViewModel

@Composable
fun IdentificationScreen(
    navController: NavHostController,
    identificationViewModel: IdentificationViewModel
) {
    val scrollState = rememberScrollState()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val expandedItems = remember { mutableStateMapOf<String, Boolean>() }
    val selectedImagesMap = remember { mutableStateMapOf<String, SnapshotStateList<Uri>>() }
    var agentFirstName by remember { mutableStateOf("Парус") }
    var agentAbout by remember { mutableStateOf("Текст") }
    var agentCity by remember { mutableStateOf("Город, район") }
    var agentFullAddress by remember { mutableStateOf("ул.Абая, д.123, кв.12") }
    var agentWorkingHours by remember { mutableStateOf("с 9 до 17, обед с 13-14") }
    var isUpdateSuccess by remember { mutableStateOf(false) }
    var isUploadSuccess by remember { mutableStateOf(false) }
    val agencyItems = identificationViewModel.agencyItems.value?.split(";") ?: emptyList()
    val checkAndNavigate = {
        if (isUpdateSuccess && isUploadSuccess) {
            isLoading = false
            navController.navigate(NavPath.PROFILE.name)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(state = scrollState)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color(0xFF566982),
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.26f))
            Text(
                text = "Верификация",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xff01060E)
            )
        }
        CustomTextField(
            label = "Название",
            value = agentFirstName,
            onValueChange = { agentFirstName = it })
        CustomTextField(
            label = "Описание",
            value = agentAbout,
            onValueChange = { agentAbout = it },
            isMultiline = true
        )

        TextSectionTitle("Адрес работы")
        CustomTextField(label = "Город", value = agentCity, onValueChange = { agentCity = it })
        CustomTextField(
            label = "Полный адрес",
            value = agentFullAddress,
            onValueChange = { agentFullAddress = it })
        CustomTextField(
            label = "Рабочие часы",
            value = agentWorkingHours,
            onValueChange = { agentWorkingHours = it },
            hasIcon = true
        )
        TextSectionTitle("Прикрепите документы")
        agencyItems.forEach { item ->
            val isExpanded = expandedItems[item] ?: false
            val itemSelectedImages =
                selectedImagesMap.getOrPut(item) { SnapshotStateList() }

            DocumentRow(
                title = item.trim(),
                expanded = isExpanded,
                onClick = {
                    expandedItems[item] = !isExpanded
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (isExpanded) {
                DocumentImageWithGalleryPicker(selectedImages = itemSelectedImages)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UploadButton(
            isLoading = isLoading,
            onClick = {
                val missingPhotosItems = agencyItems.filter { item ->
                    selectedImagesMap[item]?.size != 1
                }
                if (missingPhotosItems.isNotEmpty()) {
                    errorMessage = "Загрузите все фотографии: ${missingPhotosItems.joinToString(", ")}"
                } else {
                    errorMessage = null
                    isLoading = true
                    isUpdateSuccess = false
                    isUploadSuccess = false
                    identificationViewModel.updateUser(
                        agentAbout = agentAbout,
                        agentCity = agentCity,
                        agentFullAddress = agentFullAddress,
                        agentWorkingHours = agentWorkingHours,
                        onSuccess = {
                            isUpdateSuccess = true
                            checkAndNavigate()
                        },
                        onError = { error ->
                            isLoading = false
                            errorMessage = error.localizedMessage
                        }
                    )

                    identificationViewModel.uploadImages(
                        selectedImagesMap = selectedImagesMap,
                        agencyItems = agencyItems,
                        onSuccess = {
                            isUploadSuccess = true
                            checkAndNavigate()
                        },
                        onError = { error ->
                            isLoading = false
                            errorMessage = error.localizedMessage
                        }
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun DocumentImageWithGalleryPicker(selectedImages: SnapshotStateList<Uri>) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        selectedImages.clear()
        selectedImages.addAll(uris) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_image),
            contentDescription = null,
            modifier = Modifier
                .size(75.dp)
                .clickable {
                    galleryLauncher.launch("image/*")
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        LazyRow {
            items(selectedImages) { uri ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 8.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Удалить изображение",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.TopEnd)
                            .padding(1.dp)
                            .clickable {
                                selectedImages.remove(uri)
                            }
                            .background(Color.Red, shape = CircleShape)
                    )
                }
            }
        }
    }
}
@Composable
fun DocumentRow(title: String, expanded: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color(0xff010101),
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = Color(0xff566982),
            modifier = Modifier.size(25.dp)
        )
    }
}

@Composable
fun UploadButton(isLoading: Boolean, onClick: () -> Unit) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F)),
        startX = 0f,
        endX = 600f
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(gradient, RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Загрузить документы",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.presentation.editprofile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kilt.R
import com.example.kilt.utills.enums.UserType
import com.example.kilt.core.navigation.NavPath
import com.example.kilt.presentation.editprofile.addnewimagebottomsheet.AddNewImage
import com.example.kilt.presentation.editprofile.addnewimagebottomsheet.viewmodel.AddNewImageViewModel
import com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.AddNewPhoneNumber
import com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.viewmodel.AddNewPhoneNumberViewModel
import com.example.kilt.presentation.editprofile.changeusertypebottomsheet.ChangeUserTypeBottomSheet
import com.example.kilt.presentation.editprofile.components.CustomButtonForEdit
import com.example.kilt.presentation.editprofile.components.SaveButton
import com.example.kilt.presentation.editprofile.viewmodel.EditProfileViewModel
import com.example.kilt.presentation.theme.DefaultBlack
import com.example.kilt.utills.imageKiltUrl
import com.example.kilt.presentation.login.viewModel.AuthViewModel


val gradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
)
val listColor = listOf(Color(0xFF1B278F), Color(0xFF3244E4))

@Composable
fun EditProfile(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    editProfileViewModel: EditProfileViewModel,
    addNewPhoneNumberViewModel: AddNewPhoneNumberViewModel,
    addNewImageViewModel: AddNewImageViewModel
) {
    val scrollState = rememberScrollState()
    val user = authViewModel.user.collectAsState(initial = null).value?.user
    val uiState = editProfileViewModel.uiState.value
    var openEditProfileBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by remember { mutableStateOf(false) }
    val bottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val phoneNumbers = addNewPhoneNumberViewModel.phoneNumbers.value
    var checkDeleteUserImage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        addNewPhoneNumberViewModel.loadPhoneNumbers()
        editProfileViewModel.loadUserData()
        Log.d("userCity", "EditProfile: ${uiState.userCity}")
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
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.26f))
            Text(
                text = "Редактировать",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xff01060E)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (user?.photo == "" || checkDeleteUserImage) {
                Image(
                    painter = painterResource(id = R.drawable.non_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(84.dp)
                        .background(color = Color.Transparent, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                AsyncImage(
                    model = "$imageKiltUrl${user?.photo}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(84.dp)
                        .background(color = Color.Transparent, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { openBottomSheet = true }) {
                Text(
                    text = "Заменить",
                    color = Color(0xff3244E4),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp
                )
            }
        }
        if (openBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                contentColor = Color.White,
                containerColor = Color.White,
                sheetState = bottomState,
                onDismissRequest = { openBottomSheet = false },
            ) {
                AddNewImage(
                    addNewImageViewModel,
                    onClick = { openBottomSheet = false },
                    checkUserImage = { checkDeleteUserImage = true })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Личный данные",
            color = Color(0xff01060E),
            fontWeight = FontWeight.W700,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (user?.user_type == UserType.AGENCY.value) {
            CustomTextField(
                label = "Название",
                value = user.firstname,
                onValueChange = {})
            CustomTextField(
                label = "Описание",
                value = uiState.userAbout,
                onValueChange = { editProfileViewModel.updateUserAbout(it) },
                isMultiline = true
            )

        } else {
            CustomTextField(label = "Имя", value = user?.firstname.toString(), onValueChange = {})
            CustomTextField(
                label = "Фамилия",
                value = uiState.firstname,
                onValueChange = {})

            CustomTextField(
                label = "О себе",
                value = uiState.userAbout,
                onValueChange = { editProfileViewModel.updateUserAbout(it) },
                isMultiline = true
            )
        }

        TextSectionTitle("Телефоны")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextFieldWithIcon(
            icon = painterResource(id = R.drawable.phone_icon),
            value = user?.phone ?: ""
        )
        if (phoneNumbers.isNotEmpty()) {
            phoneNumbers.forEach { phone ->
                CustomTextFiledForListPhoneNumber(
                    icon = painterResource(id = R.drawable.phone_icon),
                    value = phone,
                    onClick = { addNewPhoneNumberViewModel.deleteSecondPhoneNumber(phone) }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        CustomButtonForEdit(
            text = "Добавить еще",
            onClick = { openEditProfileBottomSheet = true },
            colorList = listColor,
            colorBrush = gradientBrush
        )
        if (openEditProfileBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                contentColor = Color.White,
                containerColor = Color.White,
                sheetState = bottomSheetState,
                onDismissRequest = {
                    openEditProfileBottomSheet = false
                    addNewPhoneNumberViewModel.clear()
                },
            ) {
                AddNewPhoneNumber(
                    addNewPhoneNumberViewModel,
                    onClick = {
                        openEditProfileBottomSheet = false
                        addNewPhoneNumberViewModel.clearSuccessState()
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (user?.user_type == UserType.OWNER.value) {
            SwitchAgentCard(editProfileViewModel, navController)
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (user?.user_type != UserType.OWNER.value) {

            Text(
                text = "Адрес работы",
                color = Color(0xff01060E),
                fontWeight = FontWeight.W700,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChooseCityTextField(navController = navController, fullAddress = uiState.userCity)
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                label = "Полный адрес",
                value = uiState.userFullAddress,
                onValueChange = { editProfileViewModel.updateUserFullAddress(it) }
            )
            CustomTextField(
                label = "График работы",
                value = uiState.userWorkHour,
                onValueChange = { editProfileViewModel.updateUserWorkHour(it) },
                hasIcon = true
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextSectionTitle("Действия")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .clickable { },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Удалить",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = Color.Red,
                lineHeight = 20.sp
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Color.Red,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        SaveButton(
            text = "Сохранить",
            enabled = editProfileViewModel.isSaveEnabled.value,
            onClick = {
                editProfileViewModel.updateUser()
                navController.navigate(NavPath.PROFILE.name)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ChooseCityTextField(
    navController: NavHostController,
    fullAddress: String
) {
    Log.d("ChooseCityTextField", "Received fullAddress: $fullAddress")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navController.navigate(NavPath.CHOOSECITYINEDIT.name) }
            .background(Color.Transparent, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFC4C9D3),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(
            text = fullAddress,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun TextSectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xff01060E),
        fontWeight = FontWeight.W700,
        fontSize = 18.sp
    )
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isMultiline: Boolean = false,
    hasIcon: Boolean = false
) {
    val enable = !(label == "Имя" || label == "Фамилия" || label == "Название")
    val textFieldHeight = if (isMultiline) 155 else 48
    Column {
        Text(
            text = label,
            color = Color(0xff01060E),
            fontSize = 18.sp,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enable,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = Color(0xFFC4C9D3),
                    RoundedCornerShape(12.dp)
                )
                .height(textFieldHeight.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = DefaultBlack,
                unfocusedTextColor = DefaultBlack,
                disabledContainerColor = Color.White,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color(0xFF6D7384)
            ),
            singleLine = !isMultiline,
            trailingIcon = if (hasIcon) {
                { Icon(Icons.Default.DateRange, contentDescription = null) }
            } else null
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun CustomTextFieldWithIcon(icon: Painter, value: String) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color(0xFF3244E4),
                modifier = Modifier.size(21.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
            val output = regex.replace(value, "$1 ($2) $3 $4 $5")
            Text(
                text = output,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                lineHeight = 22.sp,
                color = Color(0xFF01060E),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CustomTextFiledForListPhoneNumber(icon: Painter, value: String, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color(0xFF3244E4),
                modifier = Modifier.size(21.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            val regex = """(\d)(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex()
            val output = regex.replace(value, "$1 ($2) $3 $4 $5")
            Text(
                text = output,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                lineHeight = 22.sp,
                color = Color(0xFF01060E),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.clickable { onClick() }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun SwitchAgentCard(editProfileViewModel: EditProfileViewModel, navController: NavHostController) {
    var openChangeUserTypeBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Card(
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .height(162.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.Transparent,
                RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Переключиться на профиль агента",
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                color = Color(0xFF01060E),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Если вы размещаете объекты, получите доступ к подробной статистике объявлений",
                color = Color(0xFF6D7384),
                fontWeight = FontWeight.W400
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomButtonForEdit(
                text = "Переключиться",
                onClick = {
                    openChangeUserTypeBottomSheet = true
                },
                colorList = listColor,
                colorBrush = gradientBrush
            )
        }
    }
    if (openChangeUserTypeBottomSheet) {
        ModalBottomSheet(
            tonalElevation = 20.dp,
            contentColor = Color.White,
            containerColor = Color.White,
            sheetState = bottomSheetState,
            onDismissRequest = {
                openChangeUserTypeBottomSheet = false
            },
        ) {
            ChangeUserTypeBottomSheet(
                editProfileViewModel,
                onClick = { openChangeUserTypeBottomSheet = false },
                navController
            )


        }
    }
}




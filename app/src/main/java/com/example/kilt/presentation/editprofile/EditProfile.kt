@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kilt.presentation.editprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kilt.R
import com.example.kilt.domain.editprofile.model.Phone
import com.example.kilt.enums.UserType
import com.example.kilt.presentation.editprofile.viewmodel.EditProfileViewModel
import com.example.kilt.viewmodels.AuthViewModel


val gradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
)
val listColor = listOf(Color(0xFF1B278F), Color(0xFF3244E4))

@Composable
fun EditProfile(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    editProfileViewModel: EditProfileViewModel
) {
    val scrollState = rememberScrollState()
    val user = authViewModel.user.collectAsState(initial = null).value?.user
    var agentAbout by remember { mutableStateOf("Текст") }
    var agentCity by remember { mutableStateOf("Город, район") }
    var agentFullAddress by remember { mutableStateOf("ул.Абая, д.123, кв.12") }
    var agentWorkingHours by remember { mutableStateOf("с 9 до 17, обед с 13-14") }
    var openFilterBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val uiState = editProfileViewModel.editProfileUiState.value


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
            Image(
                painter = painterResource(id = R.drawable.non_image),
                contentDescription = null,
                modifier = Modifier.size(84.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Заменить",
                color = Color(0xff3244E4),
                fontWeight = FontWeight.W400,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Личный данные",
            color = Color(0xff01060E),
            fontWeight = FontWeight.W700,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(label = "Название", value = user?.firstname.toString(), onValueChange = {})

        if (user?.user_type != UserType.AGENCY.value) {
            CustomTextField(label = "Имя", value = user?.firstname.toString(), onValueChange = {})
            CustomTextField(
                label = "Фамилия",
                value = user?.lastname.toString(),
                onValueChange = {})
        }
        CustomTextField(
            label = "О себе",
            value = user?.agent_about.toString(),
            onValueChange = { agentAbout = it },
            isMultiline = true
        )
        TextSectionTitle("Телефоны")

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldWithIcon(
            icon = painterResource(id = R.drawable.phone_icon),
            value = user?.phone ?: ""
        )
        Spacer(modifier = Modifier.height(8.dp))

        CustomButtonForEdit(
            text = "Добавить еще",
            onClick = { openFilterBottomSheet = true },
            colorList = listColor,
            colorBrush = gradientBrush
        )
        if (openFilterBottomSheet) {
            ModalBottomSheet(
                tonalElevation = 20.dp,
                contentColor = Color.White,
                containerColor = Color.White,
                sheetState = bottomSheetState,
                onDismissRequest = { openFilterBottomSheet = false },
            ) {
                AddNewPhoneNumber(editProfileViewModel)

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (user?.user_type == UserType.OWNER.value) {
            SwitchAgentCard()
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
            CustomTextField(
                label = "Город",
                value = "Город, район",
                onValueChange = { agentCity = it })
            CustomTextField(
                label = "Полный адрес",
                value = "ул.Абая, д.123, кв.12",
                onValueChange = { agentFullAddress = it })
            CustomTextField(
                label = "График работы",
                value = "с 9 до 17, обед с 13-14",
                onValueChange = { agentWorkingHours = it },
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
        SaveButton {

        }
        Spacer(modifier = Modifier.height(16.dp))
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
                focusedTextColor = Color(0xFF6D7384),
                unfocusedTextColor = Color(0xFF6D7384),
                disabledContainerColor = Color.White,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = !isMultiline,
            maxLines = if (isMultiline) 5 else 1,
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
fun CustomButtonForEdit(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorList: List<Color>,
    colorBrush: Brush
) {
    val tintColor = if (text == "Удалить аккаунт") Color(0xFFE63312) else Color(0xFF1B278F)
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = colorBrush,
                shape = RoundedCornerShape(12.dp)
            )
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = tintColor
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = text,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = colorList,
                            tileMode = TileMode.Mirror
                        ),
                    ), fontWeight = FontWeight.W700
                )
            }
        }
    }
}

@Composable
fun SwitchAgentCard() {
    Card(
        onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
                colorList = listColor,
                colorBrush = gradientBrush
            )
        }
    }
}

@Composable
fun SaveButton(onClick: () -> Unit) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F)),
        startX = 0f,
        endX = 600f
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { onClick()
            },
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Сохранить",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}


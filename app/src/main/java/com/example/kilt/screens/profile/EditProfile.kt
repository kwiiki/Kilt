package com.example.kilt.screens.profile

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.enums.UserType
import com.example.kilt.navigation.NavPath
import com.example.kilt.viewmodels.AuthViewModel


val gradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF3244E4), Color(0xFF1B278F))
)
val redGradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFFE63312), Color(0xFFE63312))
)
val listColor = listOf(Color(0xFF1B278F), Color(0xFF3244E4))
val redListColor = listOf(Color(0xFFE63312), Color(0xFFE63312))

@Composable
fun EditProfile(navController: NavHostController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val user = authViewModel.user.collectAsState(initial = null).value?.user

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

//        About me

        CustomTextField(label = "Имя", value = user?.firstname.toString())
        CustomTextField(label = "Фамилия", value = user?.lastname.toString())
        CustomTextField(label = "О себе", value = "Текст", isMultiline = true)

        // Phone number
        TextSectionTitle("Телефоны")

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldWithIcon(
            icon = painterResource(id = R.drawable.phone_icon),
            value = user?.phone ?: ""
        )
        Spacer(modifier = Modifier.height(8.dp))

        CustomButtonForEdit(
            text = "Добавить еще",
            onClick = { /*TODO*/ },
            colorList = listColor,
            colorBrush = gradientBrush
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (user?.user_type == UserType.OWNER.value) {
            SwitchAgentCard()
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (user?.user_type == UserType.AGENT.value) {

            TextSectionTitle("Адрес работы")
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(label = "Город", value = "Город, район")
            CustomTextField(label = "Полный адрес", value = "ул.Абая, д.123, кв.12")
            CustomTextField(
                label = "График работы",
                value = "с 9 до 17, обед с 13-14",
                hasIcon = true
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextSectionTitle("Действия")
        Spacer(modifier = Modifier.height(8.dp))

        CustomButtonForEdit(
            text = "Удалить аккаунт",
            onClick = { /*TODO*/ },
            colorList = redListColor,
            colorBrush = redGradientBrush
        )

        Spacer(modifier = Modifier.height(10.dp))
        SaveButton()
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
    isMultiline: Boolean = false,
    hasIcon: Boolean = false
) {

    val enable = !(label == "Имя" || label == "Фамилия")
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
            onValueChange = {},
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
                { Icon(Icons.Default.Search, contentDescription = null) }
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
fun SaveButton(){
    val gradient = Brush.horizontalGradient(
        colors = listOf( Color(0xFF3244E4),Color(0xFF1B278F)),
        startX = 0f,
        endX = 600f
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = {
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
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Search",
                        tint = Color.White
                    )
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

@Composable
@Preview(showBackground = true)
fun PreviewSwitch() {
    SwitchAgentCard()
}

@Composable
@Preview(showBackground = true)
fun PreviewEditProfile() {
    EditProfile(navController = rememberNavController(), authViewModel = hiltViewModel())
}
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.kilt.R
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable
fun Calculator(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = onClick),  // Corrected this line
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.calculator_icon),
            contentDescription = null,
            tint = Color(0xff3F4FE0)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Калькулятор ипотеки",
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            color = Color(0xff3F4FE0)
        )
        Spacer(modifier = Modifier.fillMaxWidth(0.80f))
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xff3F4FE0),
            modifier = Modifier.size(45.dp)
        )
    }
}


@Composable
fun BottomSheetContent(
    price: String,
    onHideButtonClick: () -> Unit
) {
    // State variables to store user input
    var propertyPrice by remember { mutableStateOf(price) }
    var initialPayment by remember {
        mutableStateOf((price.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ZERO)
            .multiply(BigDecimal("0.1"))
            .setScale(0, RoundingMode.HALF_UP)
            .toString()
        )
    }
    var interestRate by remember { mutableStateOf("16") }
    var loanTerm by remember { mutableStateOf("240") }

    fun formatNumber(number: String): String {
        val digitsOnly = number.filter { it.isDigit() }
        return if (digitsOnly.isNotEmpty()) {
            digitsOnly.toBigDecimal().toString().reversed().chunked(3).joinToString(" ").reversed()
        } else {
            ""
        }
    }

    // Calculate the percentage of the initial payment
    val initialPaymentPercentage = remember(propertyPrice, initialPayment) {
        val propertyPriceValue = propertyPrice.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ONE
        val initialPaymentValue = initialPayment.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ZERO
        if (propertyPriceValue > BigDecimal.ZERO) {
            initialPaymentValue.divide(propertyPriceValue, 4, RoundingMode.HALF_UP).multiply(BigDecimal(100))
        } else {
            BigDecimal.ZERO
        }
    }

    // Calculate the monthly payment using BigDecimal
    val monthlyPayment = remember(propertyPrice, initialPayment, interestRate, loanTerm) {
        val propertyPriceValue = propertyPrice.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val initialPaymentValue = initialPayment.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val interestRateValue = interestRate.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val loanTermValue = loanTerm.toIntOrNull() ?: 0

        if (initialPaymentValue > BigDecimal.ZERO && initialPaymentValue < propertyPriceValue) {
            calculateMonthlyPayment(
                propertyPrice = propertyPriceValue,
                initialPayment = initialPaymentValue,
                annualInterestRate = interestRateValue,
                loanTermInMonths = loanTermValue
            )
        } else {
            null
        }
    }

    Column(
        modifier = Modifier
            .height(600.dp)
            .background(Color(0xffFFFFFF))
            .padding(horizontal = 8.dp)
    ) {
        TopBar(onHideButtonClick)

        Text(
            text = "Рассчитайте ежемесячный платёж по ипотеке",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        InputField(
            label = "Стоимость недвижимости",
            value = formatNumber(propertyPrice),
            onValueChange = { newValue ->
                val digitsOnly = newValue.filter { it.isDigit() }
                if (digitsOnly.isNotEmpty() && digitsOnly != "0") {
                    propertyPrice = digitsOnly
                    // Обновляем первоначальный взнос при изменении стоимости недвижимости
                    initialPayment = (digitsOnly.toBigDecimalOrNull() ?: BigDecimal.ZERO)
                        .multiply(BigDecimal("0.1"))
                        .setScale(0, RoundingMode.HALF_UP)
                        .toString()
                }
            }
        )
        InputField(
            label = "Первоначальный взнос",
            value = formatNumber(initialPayment),
            onValueChange = { newValue ->
                val propertyPriceValue = propertyPrice.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ZERO
                val inputValue = newValue.filter { it.isDigit() }.toBigDecimalOrNull() ?: BigDecimal.ZERO
                if (inputValue > BigDecimal.ZERO && inputValue < propertyPriceValue) {
                    initialPayment = inputValue.toString()
                }
            },
            percentage = "%.2f%%".format(initialPaymentPercentage),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            InputField(
                label = "Ставка",
                value = interestRate,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.isNotEmpty() && digitsOnly.toInt() in 1..100) {
                        interestRate = digitsOnly
                    }
                },
                suffix = "%",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InputField(
                label = "Срок займа",
                value = loanTerm,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.isNotEmpty() && digitsOnly.toInt() > 0) {
                        loanTerm = digitsOnly
                    }
                },
                suffix = "мес.",
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Ежемесячный платёж:",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val gradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF3244E4),
                Color(0xFF1B278F)
            )
        )

        val textStyle = SpanStyle(
            brush = gradient,
            fontSize = 32.sp,
            fontWeight = FontWeight.W700
        )

        if (monthlyPayment != null) {
            BasicText(
                text = buildAnnotatedString {
                    withStyle(style = textStyle) {
                        append("%,d ₸".format(monthlyPayment.toLong()))
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            Text(
                text = "Введите корректные данные для расчета",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        BottomDetails(modifier = Modifier.zIndex(1f))
    }
}
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    percentage: String? = null,
    suffix: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xffFFFFFF))
                .border(width = 1.5.dp, color = Color(0xffDBDFE4), RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var textFieldValue by remember { mutableStateOf(value) }

            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    // Filter to allow only digits, excluding '0' as the first character
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.isNotEmpty() && digitsOnly != "0") {
                        val formattedValue = formatNumber(digitsOnly)
                        textFieldValue = formattedValue
                        onValueChange(digitsOnly)
                    } else if (digitsOnly.isEmpty()) {
                        textFieldValue = ""
                        onValueChange("")
                    }
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xffFFFFFF), shape = RoundedCornerShape(16.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        innerTextField()
                    }
                }
            )
            if (suffix != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = suffix,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black
                )
            }
            if (percentage != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = percentage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xff010101)
                )
            }
        }
    }
}

fun formatNumber(number: String): String {
    val digitsOnly = number.filter { it.isDigit() }
    return if (digitsOnly.isNotEmpty()) {
        digitsOnly.reversed().chunked(3).joinToString(" ").reversed()
    } else {
        ""
    }
}

fun calculateMonthlyPayment(
    propertyPrice: BigDecimal,
    initialPayment: BigDecimal,
    annualInterestRate: BigDecimal,
    loanTermInMonths: Int
): BigDecimal {
    if (loanTermInMonths == 0 || propertyPrice <= initialPayment) return BigDecimal.ZERO

    val loanAmount = propertyPrice - initialPayment
    val monthlyInterestRate = annualInterestRate.divide(BigDecimal(1200), 10, RoundingMode.HALF_UP)

    val monthlyPayment = (loanAmount * (BigDecimal.ONE + monthlyInterestRate * BigDecimal(loanTermInMonths))) / BigDecimal(loanTermInMonths)

    return monthlyPayment.setScale(0, RoundingMode.HALF_UP)
}
@Preview(showBackground = true)
@Composable
fun PreviewBottomSheet() {
    BottomSheetContent("222222") {

    }
}

@Composable
fun TopBar(onHideButtonClick: () -> Unit) {
    Box(contentAlignment = Alignment.TopCenter) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHideButtonClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xff566982),

                    )
            }

            Spacer(modifier = Modifier.width(50.dp))

            Text(
                text = "Калькулятор ипотеки",
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                color = Color(0xff010101)
            )
        }
    }
}
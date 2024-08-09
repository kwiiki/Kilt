package com.example.kilt.screens.searchpage.homedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ApartmentDescription() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Квартира на аренду",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 18.sp
        )

        Text(
            text = "1. Район расположения:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text("• ул. Панфилова уг.ул.Абая \"Золотой квадрат\"")
        Text("• КИМЭП расположен на расстоянии 2-х кварталов")
        Text("• Строительный колледж - через ул.Абая")
        Text("• Станция Метро - на расстоянии 1-го квартала")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "2. Дом:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text("• Не типовой, индивидуальный проект")
        Text("• Сейсмоусиленный, кирпичный, 5-ти этажный")
        Text("• Во дворе густо расположены зелёные насаждения. Из-за этого в квартире тихо")
        Text("• Имеется детская площадка")
        Text("• Въезд во двор перекрыт с двух сторон шлагбаумами")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "3. Квартира:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text("• 3-х комнатная. 4-ый этаж 5-ти этажного дома")
        Text("• Площадь 92 кв. метра")
        Text("• Санузел раздельный")
        Text("• Пол паркетный")
        Text("• Полностью меблирована. Бытовая и электронная техника (потолочные вентиляторы, два плоских телевизора, кондиционер, стиральная машина, холодильник)")
        Text("• Большая лоджия и длинный балкон")
        Text("• Оборудована полками вместительная кладовая")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Заявленная стоимость аренды - 450 тыс. тенге в месяц. Торг уместен.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Приоритет - семейным парам без маленьких детей и животных.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }
}
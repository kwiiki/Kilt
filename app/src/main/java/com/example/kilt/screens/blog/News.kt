package com.example.kilt.screens.blog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kilt.R
import com.example.kilt.screens.home.LazyRowForBlog


@Composable
fun News(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        val img1 = painterResource(id = R.drawable.blogphoto2)
        val img2 = painterResource(id = R.drawable.blogphoto1)

        Image(
            painter = img1,
            contentDescription = "blogphoto1",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(text = "6 июля, 2022 года", style = MaterialTheme.typography.labelSmall)

            Text(
                text = "Когда в присоединённых районах Алматы появится качественная вода",
                fontSize = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelMedium,
                lineHeight = 25.sp
            )

            Text(
                text = "Проблема качественного водоснабжения в присоединённых районах Алматы постепенно решается. До конца 2022 года власти обещают сдать в эксплуатацию новый водозабор, который обеспечит центральным водоснабжением жителей нескольких микрорайонов.\n" +
                        "", lineHeight = 24.sp, style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "По заверению акима города Ерболата Досаева, в этом году завершится" +
                        " строительство станции на р. Каргалы. Качественная вода поступит в дома мкр" +
                        " Карагайлы, Каргалы, Курамыс, Тастыбулак и Таусамалы. Также в этом году начнут строить " +
                        "станцию на р. Аксай и завершат разработку ПСД водозабора Ерменсай.",
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyLarge
            )

            Image(
                painter = img2,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Запуск объектов обеспечит центральным водоснабжением более 150 тысяч жителей.\n" +
                        "Кроме того, продолжается строительство водопровода (в этом году планируется сдать более 371 км) и канализации (более 260 км).",
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Параллельно решается вопрос с электросетями: строится две электроподстанции. В этом году запустят одну из них — «Алмагуль». Запуск второй намечен на июль 2023 года. Также до конца года модернизируют 138 км электросетей.",
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyLarge

            )

              LazyRowForBlog(modifier = Modifier, navController = navController)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewNews() {
    val navController = rememberNavController()
    News(navController)
}
package com.example.tickets.ui
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import com.example.tickets.R

@Composable
fun SearchTicketsScreen(nav: NavHostController) {

    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val tickets = listOf(
        Triple("Минск — Гродно", "12.12.2025", "17.90 BYN"),
        Triple("Минск — Брест", "11.01.2026", "21.40 BYN"),
        Triple("Гродно — Витебск", "03.02.2026", "30.10 BYN"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAD0C2))
            .padding(16.dp)
    ) {

        Text(
            "Поиск билетов",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        var fieldsHeight by remember { mutableStateOf(0) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {

            Column(
                Modifier
                    .weight(1f)
                    .onGloballyPositioned { coordinates ->
                        fieldsHeight = coordinates.size.height   // высота всей колонки
                    }
            ) {
                OutlinedTextField(
                    value = from,
                    onValueChange = { from = it },
                    label = { Text("Откуда") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = to,
                    onValueChange = { to = it },
                    label = { Text("Куда") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Дата") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .height(with(LocalDensity.current) { fieldsHeight.toDp() })  // кнопка = высота полей
                    .width(90.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Поиск", textAlign = TextAlign.Center)
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                modifier = Modifier.weight(1f)
            ) {
                Text("Фильтры")
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                modifier = Modifier.weight(1f)
            ) {
                Text("Сортировка")
            }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tickets) { (route, dateString, price) ->
                TicketCard(route, dateString, price, nav)
            }
        }
    }
}


@Composable
fun TicketCard(route: String, date: String, price: String, nav: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = {
            nav.navigate("ticket_details/$route/$date/$price")
        },
        colors = CardDefaults.cardColors(Color(0xFFD2AC97)),
        shape = RoundedCornerShape(16.dp)
    )  {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(route, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(6.dp))
                Text(date)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(price, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

package com.example.tickets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tickets.data.DatabaseModule
import com.example.tickets.data.entity.RouteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDetailsScreen(routeId: Long, nav: NavHostController) {
    val repository = DatabaseModule.getRepository()
    var route by remember { mutableStateOf<RouteEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Загружаем данные рейса из базы данных
    LaunchedEffect(routeId) {
        if (routeId > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                val loadedRoute = repository.getRouteById(routeId)
                CoroutineScope(Dispatchers.Main).launch {
                    route = loadedRoute
                    isLoading = false
                }
            }
        } else {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали рейса") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEAD0C2)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFEAD0C2))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(32.dp),
                    color = Color(0xFF622A3A)
                )
            } else if (route == null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color(0xFFD2AC97))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Рейс не найден",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color(0xFFD2AC97))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            route!!.route,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                BulletText("${route!!.departureDate} ${route!!.departureTime}")
                                if (route!!.trainType != null) {
                                    BulletText("Тип поезда: ${route!!.trainType}")
                                }
                                if (route!!.availableSeats > 0) {
                                    BulletText("Свободных мест: ${route!!.availableSeats}")
                                }
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Линия времени
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    route!!.departureTime,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    route!!.from,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                if (route!!.arrivalTime != null) {
                                    route!!.arrivalTime?.let {
                                        Text(
                                            it,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        route!!.to,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xFF6B2E3A))
                        )

                        Spacer(Modifier.height(8.dp))

                        if (route!!.duration != null) {
                            route!!.duration?.let {
                                Text(
                                    it,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Информация о цене
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Цена:",
                                fontSize = 18.sp
                            )
                            Text(
                                route!!.price,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF622A3A)
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        Button(
                            onClick = { nav.navigate("checkout/${routeId}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Заказать билеты", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BulletText(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("•  ", fontSize = 16.sp)
        Text(text, fontSize = 16.sp)
    }
}

package com.example.tickets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun ComfortAndPaymentScreen(routeId: Long, nav: NavHostController) {
    val repository = DatabaseModule.getRepository()
    var route by remember { mutableStateOf<RouteEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var totalPrice by remember { mutableStateOf(0.0) }
    
    LaunchedEffect(routeId) {
        if (routeId > 0) {
            isLoading = true
            CoroutineScope(Dispatchers.IO).launch {
                val loadedRoute = repository.getRouteById(routeId)
                CoroutineScope(Dispatchers.Main).launch {
                    route = loadedRoute
                    isLoading = false
                    // Извлекаем числовое значение цены для расчетов
                    route?.price?.let { priceStr ->
                        val priceValue = priceStr.replace(",", ".").replace(" BYN", "").toDoubleOrNull() ?: 0.0
                        totalPrice = priceValue
                    }
                }
            }
        } else {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Выберите места") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
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
                .background(Color(0xFFF7F1EE))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF622A3A))
                }
            } else {
                route?.let { TicketSummaryCard(it) }   // Короткая сводка билета
            }

            Spacer(Modifier.height(24.dp))

            Text("Добавьте спокойствия и комфорта",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            InsuranceCard()
            Spacer(Modifier.height(16.dp))

            RefundCard()
            Spacer(Modifier.height(16.dp))

            SmsCard()

            Spacer(Modifier.height(24.dp))

            Text("Выберите способ оплаты",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            PaymentMethods()

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { nav.navigate("confirmation_screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Продолжить", fontSize = 17.sp, color = Color.White)
                    Text(
                        route?.price ?: "0 BYN",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "Остался 1 шаг",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
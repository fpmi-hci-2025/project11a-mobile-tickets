package com.example.tickets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tickets.R
import com.example.tickets.data.DatabaseModule
import com.example.tickets.data.entity.TicketEntity
import com.example.tickets.data.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(nav: NavHostController) {
    val repository = DatabaseModule.getRepository()
    val authManager = DatabaseModule.getAuthManager()
    
    var isLoggedIn by remember { mutableStateOf(authManager.isLoggedIn()) }
    var authenticatedUser by remember { mutableStateOf<UserEntity?>(null) }
    
    // Получаем данные пользователя
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            val userId = authManager.getCurrentUserId()
            CoroutineScope(Dispatchers.IO).launch {
                val user = repository.getAuthenticatedUser(userId)
                CoroutineScope(Dispatchers.Main).launch {
                    authenticatedUser = user
                }
            }
        }
    }
    
    val userFlow by repository.getCurrentUser().collectAsState(initial = null)
    val displayUser = authenticatedUser ?: userFlow
    
    // Получаем все билеты
    val tickets by repository.getAllTickets().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAD0C2))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            "Профиль",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    displayUser?.name ?: "Имя Фамилия", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.Bold
                )
                Text(displayUser?.email ?: "email@example.com")
                Text(displayUser?.phone ?: "+375 (29) 111-22-33")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Кнопка выхода
        if (isLoggedIn) {
            Button(
                onClick = {
                    authManager.logout()
                    nav.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Выйти из аккаунта")
            }
        } else {
            Button(
                onClick = { nav.navigate("login") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Войти в аккаунт")
            }
        }

        Spacer(Modifier.height(32.dp))

        Text("Купленные билеты", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(Modifier.height(12.dp))

        if (tickets.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(Color(0xFFD2AC97))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "У вас пока нет купленных билетов",
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                tickets.forEach { ticket ->
                    TicketCard(ticket)
                }
            }
        }
    }
}

@Composable
fun TicketCard(ticket: TicketEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color(0xFFD2AC97))
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(ticket.route, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(4.dp))
            Text("Дата: ${ticket.departureDate}")
            Text("Время: ${ticket.departureTime}")
            if (ticket.seat != null) {
                Text("Место: ${ticket.seat}")
            }
            if (ticket.price.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text("Цена: ${ticket.price}", fontWeight = FontWeight.Medium)
            }
        }
    }
}

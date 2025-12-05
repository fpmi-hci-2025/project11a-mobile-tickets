package com.example.tickets.ui
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import com.example.tickets.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComfortAndPaymentScreen(nav: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Выберите места") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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

            TicketSummaryCard()   // Короткая сводка билета

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
                    Text("12,36 BYN", fontWeight = FontWeight.Bold, color = Color.White)
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
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun SupportScreen(nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAD0C2))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color(0xFFD2AC97))
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Нужна помощь? Мы на связи!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                Text("Позвоните по номеру телефона")
                Spacer(Modifier.height(8.dp))

                Text("А1: 8 (029) 945-32-11")
                Text("МТС: 0 (044) 954-32-11")
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {  nav.navigate("support_chat") },
            colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Написать в чат")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { nav.navigate("main") },
            colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вернуться на главную")
        }
    }
}

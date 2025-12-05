package com.example.tickets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tickets.R

@Composable
fun ConfirmationScreen(nav: NavHostController) {
    Scaffold(
        containerColor = Color(0xFFF7F1EE)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Иконка успеха
            Image(
                painter = painterResource(R.drawable.ic_success),
                contentDescription = "Успешно",
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(32.dp))

            // Заголовок
            Text(
                text = "Приятного путешествия!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF622A3A),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // Основной текст
            Text(
                text = "Ваш заказ успешно оплачен.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(Modifier.height(16.dp))

            // Дополнительная информация
            Text(
                text = "Билеты отправлены на электронную почту и в ваш личный кабинет.\n\n" +
                        "Не забудьте взять в поездку оригиналы документов, указанных при покупке.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(48.dp))

            // Кнопка на главную
            Button(
                onClick = {
                    // Переходим на главный экран и очищаем стек навигации
                    nav.navigate("main") {
                        popUpTo(0) // Очищаем весь стек навигации
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF622A3A),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "На главную",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
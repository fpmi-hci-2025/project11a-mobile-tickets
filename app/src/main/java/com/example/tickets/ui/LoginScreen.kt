package com.example.tickets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tickets.data.DatabaseModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(nav: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val repository = DatabaseModule.getRepository()
    val authManager = DatabaseModule.getAuthManager()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAD0C2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Вход в аккаунт",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF622A3A),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color(0xFFD2AC97))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        errorMessage = ""
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF622A3A),
                        focusedLabelColor = Color(0xFF622A3A)
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        errorMessage = ""
                    },
                    label = { Text("Пароль") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF622A3A),
                        focusedLabelColor = Color(0xFF622A3A)
                    )
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Заполните все поля"
                            return@Button
                        }
                        
                        isLoading = true
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val user = repository.login(email.trim(), password)
                                if (user != null) {
                                    authManager.setCurrentUserId(user.id)
                                    CoroutineScope(Dispatchers.Main).launch {
                                        nav.navigate("main") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        errorMessage = "Неверный email или пароль"
                                        isLoading = false
                                    }
                                }
                            } catch (e: Exception) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    errorMessage = "Ошибка входа: ${e.message}"
                                    isLoading = false
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Войти", fontSize = 18.sp)
                    }
                }

                TextButton(
                    onClick = { nav.navigate("register") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Нет аккаунта? Зарегистрироваться",
                        color = Color(0xFF622A3A),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


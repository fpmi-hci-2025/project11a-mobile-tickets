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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(nav: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var dateStart by remember { mutableStateOf("") }
    var dateEnd by remember { mutableStateOf("") }

    val allFieldsFilled = from.isNotBlank() && to.isNotBlank()

    val allFieldsEmpty = from.isBlank() && to.isBlank()

    val isSearchEnabled = allFieldsFilled || allFieldsEmpty

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(0.dp),
                modifier = Modifier.width(260.dp)
            ) {

                Text(
                    "Меню",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                DrawerButton("Найти билеты") {
                    scope.launch { drawerState.close() }
                    nav.navigate("search")
                }

                DrawerButton("Техподдержка") {
                    scope.launch { drawerState.close() }
                    nav.navigate("support")
                }

                DrawerButton("Все акции") {
                    scope.launch { drawerState.close() }
                    nav.navigate("promo")
                }

                DrawerButton("Профиль") {
                    scope.launch { drawerState.close() }
                    nav.navigate("profile")
                }
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(48.dp),
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { nav.navigate("profile") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = "Profile"
                            )
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
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Картинка котика
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat_train),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(260.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Форма
            FormField("Пункт отправки", from) { from = it }
            Spacer(Modifier.height(8.dp))
            FormField("Пункт назначения", to) { to = it }
            Spacer(Modifier.height(8.dp))

            DateField(label = "Дата начала поездки", value = dateStart) {}
            Spacer(Modifier.height(8.dp))
            DateField(label = "Дата конца", value = dateEnd) {}

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val fromEncoded = java.net.URLEncoder.encode(from.ifBlank { " " }, "UTF-8")
                    val toEncoded = java.net.URLEncoder.encode(to.ifBlank { " " }, "UTF-8")
                    val dateStartEncoded = java.net.URLEncoder.encode(dateStart.ifBlank { " " }, "UTF-8")
                    val dateEndEncoded = java.net.URLEncoder.encode(dateEnd.ifBlank { " " }, "UTF-8")
                    nav.navigate("search/$fromEncoded/$toEncoded/$dateStartEncoded/$dateEndEncoded")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                // ДОБАВЛЯЕМ ENABLED
                enabled = isSearchEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF622A3A),
                    disabledContainerColor = Color.Gray // Цвет, когда кнопка недоступна
                )
            ) {
                Text("Найти", color = Color.White)
            }

            Spacer(Modifier.height(24.dp))

            // Техподдержка
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    Modifier
                        .background(Color(0xFFE1BCA8))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Нужна помощь?")
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = { nav.navigate("support") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF622A3A)
                        )
                    ) {
                        Text("Обратиться в техподдержку")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Акции и промокоды",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            PromoBlock(nav)


            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { nav.navigate("promo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF622A3A)
                )
            ) {
                Text("Все акции")
            }
        }
    } }}


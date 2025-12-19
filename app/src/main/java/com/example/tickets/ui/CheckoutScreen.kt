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
fun CheckoutScreen(routeId: Long, nav: NavHostController) {
    val repository = DatabaseModule.getRepository()
    var route by remember { mutableStateOf<RouteEntity?>(null) }
    var pSurname by remember { mutableStateOf("") }
    var pName by remember { mutableStateOf("") }
    var pGender by remember { mutableStateOf("") }
    var pBirthday by remember { mutableStateOf("") }
    var pDocType by remember { mutableStateOf("") }
    var pDocNumber by remember { mutableStateOf("") }
    var pPhone by remember { mutableStateOf("") }
    var pEmail by remember { mutableStateOf("") }
    // --- Form States ---

    var pDoc by remember { mutableStateOf("") }

    var bName by remember { mutableStateOf("") }
    var bSurname by remember { mutableStateOf("") }
    var bDoc by remember { mutableStateOf("") }

    var isBuyerSame by remember { mutableStateOf(true) }
    val isFormValid = listOf(
        pSurname, pName, pGender, pBirthday,
        pDocType, pDocNumber, pPhone, pEmail
    ).all { it.isNotBlank() }
    // --- Validation Logic ---
    val isPassengerValid = listOf(pSurname, pName, pGender, pBirthday, pDocType, pDocNumber, pPhone, pEmail).all { it.isNotBlank() }



    val canProceed = isPassengerValid

    LaunchedEffect(routeId) {
        if (routeId > 0) {
            val loadedRoute = repository.getRouteById(routeId)
            route = loadedRoute
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Пассажир") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFEAD0C2))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFEAD0C2))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            PassengerForm(
                title = "Пассажир",
                subtitle = "Взрослый, старше 12 лет",
                surname = pSurname, onSurnameChange = { pSurname = it },
                name = pName, onNameChange = { pName = it },
                gender = pGender, onGenderChange = { pGender = it },
                birthday = pBirthday, onBirthdayChange = { pBirthday = it },
                docType = pDocType, onDocTypeChange = { pDocType = it },
                docNumber = pDocNumber, onDocNumberChange = { pDocNumber = it },
                phone = pPhone, onPhoneChange = { pPhone = it },
                email = pEmail, onEmailChange = { pEmail = it }
            )

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isBuyerSame,
                    onCheckedChange = { isBuyerSame = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF622A3A))
                )
                Text("Этот пользователь - покупатель")
            }


            if (!isBuyerSame) {
                Spacer(Modifier.height(16.dp))
            BuyerForm()
            }

            Spacer(Modifier.height(16.dp))

            // Policy Agreement (static for now)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = true, onCheckedChange = {}, enabled = false,
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF622A3A)))
                Text("Даю согласие на обработку персональных данных")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { nav.navigate("comfort_and_payment/${routeId}") },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                enabled = canProceed,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF622A3A),
                    disabledContainerColor = Color(0xFF8D767C) // Visual feedback for disabled state
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Продолжить")
                    Text(route?.price ?: "0 BYN")
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(if (canProceed) "Все готово!" else "Заполните все поля, чтобы продолжить", fontSize = 12.sp)
        }
    }
}
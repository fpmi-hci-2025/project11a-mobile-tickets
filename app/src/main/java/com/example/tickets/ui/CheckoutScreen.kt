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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
fun CheckoutScreen(nav: NavHostController) {

    var isBuyerSame by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Пассажир") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
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
                .background(Color(0xFFEAD0C2))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {


            PassengerForm(
                title = "Пассажир",
                subtitle = "Взрослый, старше 12 лет"
            )

            Spacer(Modifier.height(12.dp))

            // чекбокс
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isBuyerSame,
                    onCheckedChange = { isBuyerSame = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF622A3A)
                    )
                )
                Text("Этот пользователь - покупатель")
            }

            Spacer(Modifier.height(16.dp))

            // Если чекбокс выключен – показываем форму покупателя
            if (!isBuyerSame) {
                BuyerForm()
            }

            Spacer(Modifier.height(16.dp))

            // соглашение
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    enabled = false,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF622A3A)
                    )
                )
                Text("Даю согласие на обработку персональных данных")
            }

            Spacer(Modifier.height(16.dp))

            // кнопка продолжить
            Button(
                onClick = { nav.navigate("comfort_and_payment") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Продолжить")
                    Text("12,36 BYN")
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Осталось 3 шага")
        }
    }
}

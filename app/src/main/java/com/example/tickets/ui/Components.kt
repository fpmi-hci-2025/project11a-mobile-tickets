package com.example.tickets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tickets.R

@Composable
fun FormField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF622A3A),
            focusedLabelColor = Color(0xFF622A3A)
        )
    )
}

@Composable
fun DateField(label: String, value: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current, // Explicitly passing it here fixes the conflict
                onClick = { onClick }
            )
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = null)
            },
            readOnly = true,
            // Важно: enabled = false делает поле визуально доступным,
            // но игнорирующим ввод, а Box выше перехватывает клик.
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color(0xFF622A3A),
                disabledTrailingIconColor = Color(0xFF622A3A),
                disabledPlaceholderColor = Color.DarkGray
            )
        )
    }
}

@Composable
fun PromoItem(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        shape = RoundedCornerShape(12.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

@Composable
fun PromoBlock(nav: NavHostController) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        PromoCard(
            text = "Скидка 10 процентов",
            modifier = Modifier.fillMaxWidth()
        ) {
            nav.navigate("promo_details/Скидка 10 процентов/57647")
        }

        PromoCard(
            text = "2 билета по цене 1",
            modifier = Modifier.fillMaxWidth()
        ) {
            nav.navigate("promo_details/2 билета по цене 1/92831")
        }
    }
}


@Composable
fun PromoCard(
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable(
                indication = null,   // ❗ предотвращает проблему IndicationNode
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        colors = CardDefaults.cardColors(Color(0xFFD2AC97)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun DrawerButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}

@Composable
fun PassengerForm(
    title: String,
    subtitle: String,
    surname: String, onSurnameChange: (String) -> Unit,
    name: String, onNameChange: (String) -> Unit,
    gender: String, onGenderChange: (String) -> Unit,
    birthday: String, onBirthdayChange: (String) -> Unit,
    docType: String, onDocTypeChange: (String) -> Unit,
    docNumber: String, onDocNumberChange: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFFD2AC97))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(subtitle)

            Spacer(Modifier.height(12.dp))

            Row {
                OutlinedTextField(
                    value = surname,
                    onValueChange = onSurnameChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Фамилия") }
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Имя") }
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                OutlinedTextField(
                    value = gender,
                    onValueChange = onGenderChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Пол") }
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = birthday,
                    onValueChange = onBirthdayChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Дата рождения") }
                )
            }

            Spacer(Modifier.height(16.dp))
            Text("Документы", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Row {
                OutlinedTextField(
                    value = docType,
                    onValueChange = onDocTypeChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Паспорт РБ") }
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = docNumber,
                    onValueChange = onDocNumberChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Номер документа") }
                )
            }

            Spacer(Modifier.height(16.dp))
            Text("Контактная информация", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Row {
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Номер телефона") }
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Почта") }
                )
            }
        }
    }
}

@Composable
fun BuyerForm() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFFD2AC97))
    ) {
        Column(Modifier.padding(16.dp)) {

            Text("Покупатель", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Отправим билет, сообщим об изменениях")

            Spacer(Modifier.height(12.dp))

            Row {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Фамилия") }
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Имя") }
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Контактная информация", fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            Row {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Номер телефона") }
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Почта") }
                )
            }
        }
    }
}


@Composable
fun TicketSummaryCard(route: com.example.tickets.data.entity.RouteEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Маршрут", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(route.from, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text(
                        "${route.departureTime}  •  ${route.departureDate}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(route.to, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    if (route.arrivalTime != null && route.arrivalDate != null) {
                        Text(
                            "${route.arrivalTime}  •  ${route.arrivalDate}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (route.trainType != null) {
                    Text("Тип: ${route.trainType}", fontSize = 14.sp, color = Color.Gray)
                }
                if (route.duration != null) {
                    Text("Длительность: ${route.duration}", fontSize = 14.sp, color = Color.Gray)
                }
            }
            
            Spacer(Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Цена: ${route.price}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF622A3A))
                if (route.availableSeats > 0) {
                    Text(
                        "Свободных мест: ${route.availableSeats}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
fun ServiceCard(
    title: String,
    price: String,
    description: String,
    items: List<String>
) {
    var selected by remember { mutableStateOf(items.last()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFFD2AC97))
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    if (description.isNotEmpty())
                        Text(description, fontSize = 14.sp)
                }

                if (price.isNotEmpty())
                    Text(price, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            items.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = LocalIndication.current, // Добавьте эту строку
                            onClick = { selected = option }
                        )
                ) {
                    RadioButton(
                        selected = selected == option,
                        onClick = { selected = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF622A3A)
                        )
                    )
                    Text(option)
                }
            }
        }
    }
}


@Composable
fun InsuranceCard() {
    var selected by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current, // Добавьте эту строку
                onClick = { selected = !selected }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selected,
                onCheckedChange = { selected = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF622A3A))
            )

            Column(Modifier.weight(1f)) {
                Text("Страхование поездки", fontWeight = FontWeight.Bold)
                Text(
                    "Компенсация при задержке или отмене рейса.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Text("1,20 BYN", fontWeight = FontWeight.Bold)
        }
    }
}



@Composable
fun RefundCard() {
    var selected by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current, // Добавьте эту строку
                onClick = { selected = !selected }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selected,
                onCheckedChange = { selected = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF622A3A))
            )

            Column(Modifier.weight(1f)) {
                Text("Гарантированный возврат", fontWeight = FontWeight.Bold)
                Text(
                    "Возврат 100% стоимости при отмене поездки.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Text("2,10 BYN", fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun SmsCard() {
    var selected by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current, // Добавьте эту строку
                onClick = { selected = !selected }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selected,
                onCheckedChange = { selected = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF622A3A))
            )

            Column(Modifier.weight(1f)) {
                Text("SMS-уведомления", fontWeight = FontWeight.Bold)
                Text(
                    "Уведомления о статусе рейса и изменениях.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Text("0,50 BYN", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PaymentMethods() {
    var selected by remember { mutableStateOf("card") }

    Column {

        PaymentMethodItem(
            title = "Банковская карта",
            icon = R.drawable.ic_card,
            isSelected = selected == "card",
            onClick = { selected = "card" }
        )

        Spacer(Modifier.height(12.dp))

        PaymentMethodItem(
            title = "Apple Pay / Google Pay",
            icon = R.drawable.ic_phone_pay,
            isSelected = selected == "phone",
            onClick = { selected = "phone" }
        )
    }
}

@Composable
fun PaymentMethodItem(title: String, icon: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current, // Добавьте эту строку
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(if (isSelected) 6.dp else 2.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(Modifier.width(12.dp))

            Text(title, fontSize = 16.sp, modifier = Modifier.weight(1f))

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF622A3A))
            )
        }
    }
}

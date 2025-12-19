package com.example.tickets.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SortDialog(
    currentSort: String,
    onSortSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Сортировка",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                SortOption(
                    label = "По времени отправления (раньше)",
                    selected = currentSort == "time",
                    onClick = { onSortSelected("time") }
                )
                
                SortOption(
                    label = "По времени отправления (позже)",
                    selected = currentSort == "time_desc",
                    onClick = { onSortSelected("time_desc") }
                )
                
                SortOption(
                    label = "По цене (дешевле)",
                    selected = currentSort == "price",
                    onClick = { onSortSelected("price") }
                )
                
                SortOption(
                    label = "По цене (дороже)",
                    selected = currentSort == "price_desc",
                    onClick = { onSortSelected("price_desc") }
                )
                
                SortOption(
                    label = "По количеству мест",
                    selected = currentSort == "seats",
                    onClick = { onSortSelected("seats") }
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF622A3A))
                ) {
                    Text("Закрыть")
                }
            }
        }
    }
}

@Composable
fun SortOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF622A3A)
            )
        )
        Text(
            label,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 16.sp
        )
    }
}


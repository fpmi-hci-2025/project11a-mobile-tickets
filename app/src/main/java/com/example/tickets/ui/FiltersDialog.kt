package com.example.tickets.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun FiltersDialog(
    trainType: String?,
    minSeats: Int,
    onTrainTypeChange: (String?) -> Unit,
    onMinSeatsChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    var selectedTrainType by remember { mutableStateOf(trainType) }
    var selectedMinSeats by remember { mutableStateOf(minSeats) }

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Фильтры",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("Тип поезда", fontWeight = FontWeight.Medium)
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedTrainType == null,
                        onClick = { selectedTrainType = null },
                        label = { Text("Все") }
                    )
                    FilterChip(
                        selected = selectedTrainType == "пассажирский",
                        onClick = { selectedTrainType = "пассажирский" },
                        label = { Text("Пассажирский") }
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text("Минимальное количество свободных мест", fontWeight = FontWeight.Medium)
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { selectedMinSeats = maxOf(0, selectedMinSeats - 5) },
                        modifier = Modifier.size(40.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF622A3A))
                    ) {
                        Text("-")
                    }
                    Text(
                        "$selectedMinSeats",
                        modifier = Modifier.width(60.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Button(
                        onClick = { selectedMinSeats += 5 },
                        modifier = Modifier.size(40.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF622A3A))
                    ) {
                        Text("+")
                    }
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            selectedTrainType = null
                            selectedMinSeats = 0
                            onTrainTypeChange(null)
                            onMinSeatsChange(0)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Сбросить")
                    }
                    Button(
                        onClick = {
                            onTrainTypeChange(selectedTrainType)
                            onMinSeatsChange(selectedMinSeats)
                            onApply()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(Color(0xFF622A3A))
                    ) {
                        Text("Применить")
                    }
                }
            }
        }
    }
}


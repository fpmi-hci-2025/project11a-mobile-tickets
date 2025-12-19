package com.example.tickets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tickets.data.DatabaseModule
import com.example.tickets.data.entity.RouteEntity

/**
 * Преобразует дату из формата DD/MM/YYYY в DD.MM.YYYY
 */
fun convertDateFormat(date: String): String {
    return if (date.contains("/")) {
        date.replace("/", ".")
    } else {
        date
    }
}

@Composable
fun SearchTicketsScreen(
    initialFrom: String = "",
    initialTo: String = "",
    initialDateStart: String = "",
    initialDateEnd: String = "",
    nav: NavHostController
) {
    val repository = DatabaseModule.getRepository()
    
    var from by remember { mutableStateOf(initialFrom) }
    var to by remember { mutableStateOf(initialTo) }
    var date by remember { mutableStateOf(initialDateStart) }
    var dateStart by remember { mutableStateOf(convertDateFormat(initialDateStart)) }
    var dateEnd by remember { mutableStateOf(convertDateFormat(initialDateEnd)) }
    var isSearching by remember { mutableStateOf(initialFrom.isNotBlank() || initialTo.isNotBlank()) }
    
    // Состояния для фильтров и сортировки
    var showFiltersDialog by remember { mutableStateOf(false) }
    var showSortDialog by remember { mutableStateOf(false) }
    var sortType by remember { mutableStateOf("time") } // time, price, duration, seats
    var filterTrainType by remember { mutableStateOf<String?>(null) }
    var filterMinSeats by remember { mutableStateOf(0) }

    // Получаем все рейсы по умолчанию
    val allRoutes by repository.getAllRoutes().collectAsState(initial = emptyList())

    // Результаты поиска по маршруту
    val searchResults by remember(from, to) {
        repository.searchRoutes(from.trim(), to.trim())
    }.collectAsState(initial = emptyList())
    // Результаты поиска по маршруту и дате
    val searchResultsByDate by repository.searchRoutesByDate(from, to, convertDateFormat(date)).collectAsState(initial = emptyList())
    
    // Результаты поиска по диапазону дат
    val searchResultsByDateRange by repository.searchRoutesByDateRange(from, to, dateStart, dateEnd).collectAsState(initial = emptyList())
    
    // Определяем какие рейсы показывать
    val routesList = remember(allRoutes, searchResults, searchResultsByDate, from, to, date) {
        val hasSearchTerms = from.isNotBlank() || to.isNotBlank()
        val hasDate = date.isNotBlank()

        when {
            // If user typed city AND date
            hasSearchTerms && hasDate -> searchResultsByDate

            // If user only typed cities
            hasSearchTerms -> searchResults

            // If everything is empty, show all
            else -> allRoutes
        }
    }
    
    // Применяем фильтры и сортировку
    val filteredAndSortedRoutes = remember(routesList, sortType, filterTrainType, filterMinSeats) {
        var result = routesList
        
        // Применяем фильтры
        if (filterTrainType != null) {
            result = result.filter { it.trainType == filterTrainType }
        }
        if (filterMinSeats > 0) {
            result = result.filter { it.availableSeats >= filterMinSeats }
        }
        
        // Применяем сортировку
        result = when (sortType) {
            "price" -> result.sortedBy { 
                it.price.replace(",", ".").replace(" BYN", "").toDoubleOrNull() ?: 0.0 
            }
            "price_desc" -> result.sortedByDescending { 
                it.price.replace(",", ".").replace(" BYN", "").toDoubleOrNull() ?: 0.0 
            }
            "time" -> result.sortedWith(compareBy({ it.departureDate }, { it.departureTime }))
            "time_desc" -> result.sortedWith(compareBy({ it.departureDate }, { it.departureTime })).reversed()
            "seats" -> result.sortedByDescending { it.availableSeats }
            else -> result
        }
        
        result
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAD0C2))
            .padding(16.dp)
    ) {

        Text(
            "Поиск билетов",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        var fieldsHeight by remember { mutableStateOf(0) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {

            Column(
                Modifier
                    .weight(1f)
                    .onGloballyPositioned { coordinates ->
                        fieldsHeight = coordinates.size.height   // высота всей колонки
                    }
            ) {
                OutlinedTextField(
                    value = from,
                    onValueChange = { from = it },
                    label = { Text("Откуда") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = to,
                    onValueChange = { to = it },
                    label = { Text("Куда") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                /*OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Дата") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )*/
                Button(
                    onClick = {
                        isSearching = true
                        // Обновляем дату для поиска
                        if (date.isBlank() && dateStart.isNotBlank()) {
                            date = dateStart
                        }
                        // Поиск выполняется автоматически через Flow
                    },
                    Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Поиск", textAlign = TextAlign.Center)
                }
            }

            Spacer(Modifier.width(12.dp))


        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { showFiltersDialog = true },
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                modifier = Modifier.weight(1f)
            ) {
                Text("Фильтры")
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = { showSortDialog = true },
                colors = ButtonDefaults.buttonColors(Color(0xFF622A3A)),
                modifier = Modifier.weight(1f)
            ) {
                Text("Сортировка")
            }
        }
        
        // Диалог фильтров
        if (showFiltersDialog) {
            FiltersDialog(
                trainType = filterTrainType,
                minSeats = filterMinSeats,
                onTrainTypeChange = { filterTrainType = it },
                onMinSeatsChange = { filterMinSeats = it },
                onDismiss = { showFiltersDialog = false },
                onApply = { showFiltersDialog = false }
            )
        }
        
        // Диалог сортировки
        if (showSortDialog) {
            SortDialog(
                currentSort = sortType,
                onSortSelected = { sortType = it },
                onDismiss = { showSortDialog = false }
            )
        }

        Spacer(Modifier.height(20.dp))

        if (filteredAndSortedRoutes.isEmpty() && isSearching) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(Color(0xFFD2AC97))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Рейсы не найдены",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredAndSortedRoutes) { route ->
                    RouteCard(route, nav)
                }
            }
        }
    }
}


@Composable
fun RouteCard(route: RouteEntity, nav: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = {
            nav.navigate("ticket_details/${route.id}")
        },
        colors = CardDefaults.cardColors(Color(0xFFD2AC97)),
        shape = RoundedCornerShape(16.dp)
    )  {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(route.route, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(6.dp))
                Text("${route.departureDate} ${route.departureTime}")
                if (route.availableSeats > 0) {
                    Text(
                        "Свободных мест: ${route.availableSeats}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(route.price, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

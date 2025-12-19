package com.example.tickets.data

import android.content.Context
import com.example.tickets.data.entity.RouteEntity
import com.example.tickets.data.entity.SearchHistoryEntity
import com.example.tickets.data.entity.TicketEntity
import com.example.tickets.data.entity.UserEntity
import com.example.tickets.data.repository.TicketsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseInitializer {
    
    /**
     * Заполняет базу данных начальными данными
     * Вызывается при первом запуске приложения
     */
    fun initializeDatabase(context: Context) {
        val database = TicketsDatabase.getDatabase(context)
        val repository = TicketsRepository(database)
        
        CoroutineScope(Dispatchers.IO).launch {
            // Проверяем, есть ли уже рейсы
            val routeCount = repository.getRouteCount()
            
            // Если рейсов нет, заполняем базу
            if (routeCount == 0) {
                populateDatabase(repository)
            }
        }
    }
    
    /**
     * Упрощенная версия - заполняет базу данных без проверки существующих данных
     * Используется для тестирования
     * Примечание: пользователи теперь создаются через регистрацию, поэтому здесь создаем только тестовые билеты и рейсы
     */
    suspend fun populateDatabase(repository: TicketsRepository) {
        // Создаем рейсы (доступные маршруты для поиска)
        val routes = listOf(
            RouteEntity(
                from = "Минск",
                to = "Гродно",
                route = "Минск — Гродно",
                departureDate = "12.12.2025",
                departureTime = "08:30",
                arrivalDate = "12.12.2025",
                arrivalTime = "12:15",
                duration = "3 ч 45 мин",
                price = "17.90 BYN",
                trainType = "пассажирский",
                availableSeats = 45
            ),
            RouteEntity(
                from = "Минск",
                to = "Брест",
                route = "Минск — Брест",
                departureDate = "11.01.2026",
                departureTime = "14:20",
                arrivalDate = "11.01.2026",
                arrivalTime = "19:00",
                duration = "4 ч 40 мин",
                price = "21.40 BYN",
                trainType = "пассажирский",
                availableSeats = 32
            ),
            RouteEntity(
                from = "Гродно",
                to = "Витебск",
                route = "Гродно — Витебск",
                departureDate = "03.02.2026",
                departureTime = "10:00",
                arrivalDate = "03.02.2026",
                arrivalTime = "16:30",
                duration = "6 ч 30 мин",
                price = "30.10 BYN",
                trainType = "пассажирский",
                availableSeats = 28
            ),
            RouteEntity(
                from = "Минск",
                to = "Брест",
                route = "Минск — Брест",
                departureDate = "12.12.2025",
                departureTime = "12:40",
                arrivalDate = "12.12.2025",
                arrivalTime = "16:32",
                duration = "5 ч 32 мин",
                price = "12,36 BYN",
                trainType = "пассажирский",
                availableSeats = 50
            ),
            RouteEntity(
                from = "Минск",
                to = "Витебск",
                route = "Минск — Витебск",
                departureDate = "15.12.2025",
                departureTime = "09:15",
                arrivalDate = "15.12.2025",
                arrivalTime = "14:45",
                duration = "5 ч 30 мин",
                price = "19.50 BYN",
                trainType = "пассажирский",
                availableSeats = 38
            ),
            RouteEntity(
                from = "Брест",
                to = "Минск",
                route = "Брест — Минск",
                departureDate = "13.12.2025",
                departureTime = "07:00",
                arrivalDate = "13.12.2025",
                arrivalTime = "11:30",
                duration = "4 ч 30 мин",
                price = "21.40 BYN",
                trainType = "пассажирский",
                availableSeats = 42
            ),
            RouteEntity(
                from = "Гродно",
                to = "Минск",
                route = "Гродно — Минск",
                departureDate = "14.12.2025",
                departureTime = "16:20",
                arrivalDate = "14.12.2025",
                arrivalTime = "20:05",
                duration = "3 ч 45 мин",
                price = "17.90 BYN",
                trainType = "пассажирский",
                availableSeats = 35
            ),
            RouteEntity(
                from = "Минск",
                to = "Гомель",
                route = "Минск — Гомель",
                departureDate = "16.12.2025",
                departureTime = "11:00",
                arrivalDate = "16.12.2025",
                arrivalTime = "15:20",
                duration = "4 ч 20 мин",
                price = "18.75 BYN",
                trainType = "пассажирский",
                availableSeats = 40
            )
        )
        
        repository.insertRoutes(routes)
        
        // Создаем билеты (пользователи создаются через регистрацию)
        val tickets = listOf(
            TicketEntity(
                route = "Минск - Брест",
                departureDate = "12.12.2025",
                departureTime = "12:40",
                arrivalDate = "12.12.2025",
                arrivalTime = "16:32",
                duration = "5 ч 32 мин",
                seat = "17A",
                trainType = "пассажирский",
                price = "12,36 BYN",
                passengerName = "Имя Фамилия",
                passengerEmail = "email@example.com",
                passengerPhone = "+375 (29) 111-22-33",
                status = "active"
            ),
            TicketEntity(
                route = "Минск — Гродно",
                departureDate = "12.12.2025",
                departureTime = "08:30",
                arrivalDate = "12.12.2025",
                arrivalTime = "12:15",
                duration = "3 ч 45 мин",
                seat = "5B",
                trainType = "пассажирский",
                price = "17.90 BYN",
                passengerName = "Имя Фамилия",
                passengerEmail = "email@example.com",
                passengerPhone = "+375 (29) 111-22-33",
                status = "active"
            ),
            TicketEntity(
                route = "Минск — Брест",
                departureDate = "11.01.2026",
                departureTime = "14:20",
                arrivalDate = "11.01.2026",
                arrivalTime = "19:00",
                duration = "4 ч 40 мин",
                seat = "12C",
                trainType = "пассажирский",
                price = "21.40 BYN",
                passengerName = "Имя Фамилия",
                passengerEmail = "email@example.com",
                passengerPhone = "+375 (29) 111-22-33",
                status = "active"
            ),
            TicketEntity(
                route = "Гродно — Витебск",
                departureDate = "03.02.2026",
                departureTime = "10:00",
                arrivalDate = "03.02.2026",
                arrivalTime = "16:30",
                duration = "6 ч 30 мин",
                seat = "8A",
                trainType = "пассажирский",
                price = "30.10 BYN",
                passengerName = "Имя Фамилия",
                passengerEmail = "email@example.com",
                passengerPhone = "+375 (29) 111-22-33",
                status = "active"
            )
        )
        
        tickets.forEach { ticket ->
            repository.insertTicket(ticket)
        }
        
        // Создаем историю поиска
        val searchHistory = listOf(
            SearchHistoryEntity(
                from = "Минск",
                to = "Брест",
                dateStart = "26/09/2025",
                dateEnd = "26/09/2025"
            ),
            SearchHistoryEntity(
                from = "Минск",
                to = "Гродно",
                dateStart = "12/12/2025",
                dateEnd = "12/12/2025"
            )
        )
        
        searchHistory.forEach { search ->
            repository.insertSearch(search)
        }
    }
}


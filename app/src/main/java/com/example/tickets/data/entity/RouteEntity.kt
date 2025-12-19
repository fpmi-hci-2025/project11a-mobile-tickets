package com.example.tickets.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность для хранения доступных рейсов (маршрутов) для поиска
 * Отличается от TicketEntity тем, что это доступные рейсы, а не купленные билеты
 */
@Entity(tableName = "routes")
data class RouteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val from: String, // Откуда, например "Минск"
    val to: String, // Куда, например "Брест"
    val route: String, // Полный маршрут, например "Минск — Брест"
    val departureDate: String, // Дата отправления, например "12.12.2025"
    val departureTime: String, // Время отправления, например "12:40"
    val arrivalDate: String?, // Дата прибытия
    val arrivalTime: String?, // Время прибытия, например "16:32"
    val duration: String?, // Длительность поездки, например "5 ч 32 мин"
    val price: String, // Цена, например "17.90 BYN"
    val trainType: String? = "пассажирский", // Тип поезда
    val availableSeats: Int = 50 // Количество свободных мест
)


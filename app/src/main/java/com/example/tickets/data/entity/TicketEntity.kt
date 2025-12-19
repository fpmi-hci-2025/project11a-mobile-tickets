package com.example.tickets.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val route: String, // e.g., "Минск - Брест"
    val departureDate: String, // e.g., "12.12.2025"
    val departureTime: String, // e.g., "12:40"
    val arrivalDate: String?,
    val arrivalTime: String?,
    val duration: String?, // e.g., "5 ч 32 мин"
    val seat: String?, // e.g., "17A"
    val trainType: String?, // e.g., "пассажирский"
    val price: String, // e.g., "12,36 BYN"
    val passengerName: String,
    val passengerEmail: String?,
    val passengerPhone: String?,
    val purchaseDate: Long = System.currentTimeMillis(), // timestamp
    val status: String = "active" // active, cancelled, used
)


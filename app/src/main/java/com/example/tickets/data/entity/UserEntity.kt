package com.example.tickets.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String,
    val password: String = "", // Пароль для авторизации
    val isBuyer: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)


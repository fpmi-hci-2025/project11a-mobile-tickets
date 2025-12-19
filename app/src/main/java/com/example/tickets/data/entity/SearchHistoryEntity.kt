package com.example.tickets.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val from: String,
    val to: String,
    val dateStart: String,
    val dateEnd: String?,
    val searchDate: Long = System.currentTimeMillis()
)


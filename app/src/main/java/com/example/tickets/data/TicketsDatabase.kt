package com.example.tickets.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tickets.data.dao.RouteDao
import com.example.tickets.data.dao.SearchHistoryDao
import com.example.tickets.data.dao.TicketDao
import com.example.tickets.data.dao.UserDao
import com.example.tickets.data.entity.RouteEntity
import com.example.tickets.data.entity.SearchHistoryEntity
import com.example.tickets.data.entity.TicketEntity
import com.example.tickets.data.entity.UserEntity

@Database(
    entities = [TicketEntity::class, UserEntity::class, SearchHistoryEntity::class, RouteEntity::class],
    version = 3,
    exportSchema = false
)
abstract class TicketsDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun userDao(): UserDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var INSTANCE: TicketsDatabase? = null

        fun getDatabase(context: Context): TicketsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TicketsDatabase::class.java,
                    "tickets_database"
                )
                    .fallbackToDestructiveMigration() // For development - removes data on schema change
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


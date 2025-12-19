package com.example.tickets.data

import android.content.Context
import com.example.tickets.data.repository.TicketsRepository

/**
 * Helper object to provide database and repository instances
 * Use this to access the database throughout your app
 */
object DatabaseModule {
    private var database: TicketsDatabase? = null
    private var repository: TicketsRepository? = null
    private var authManager: AuthManager? = null

    fun initialize(context: Context) {
        if (database == null) {
            database = TicketsDatabase.getDatabase(context)
            repository = TicketsRepository(database!!)
            authManager = AuthManager(context)
        }
    }

    fun getRepository(): TicketsRepository {
        return repository ?: throw IllegalStateException("DatabaseModule not initialized. Call initialize() first.")
    }

    fun getDatabase(): TicketsDatabase {
        return database ?: throw IllegalStateException("DatabaseModule not initialized. Call initialize() first.")
    }
    
    fun getAuthManager(): AuthManager {
        return authManager ?: throw IllegalStateException("DatabaseModule not initialized. Call initialize() first.")
    }
}


package com.example.tickets.data.repository

import com.example.tickets.data.TicketsDatabase
import com.example.tickets.data.entity.RouteEntity
import com.example.tickets.data.entity.SearchHistoryEntity
import com.example.tickets.data.entity.TicketEntity
import com.example.tickets.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class TicketsRepository(private val database: TicketsDatabase) {
    
    // Ticket operations
    fun getAllTickets(): Flow<List<TicketEntity>> = database.ticketDao().getAllTickets()
    
    suspend fun getTicketById(id: Long): TicketEntity? = database.ticketDao().getTicketById(id)
    
    fun getTicketsByStatus(status: String): Flow<List<TicketEntity>> = 
        database.ticketDao().getTicketsByStatus(status)
    
    suspend fun insertTicket(ticket: TicketEntity): Long = database.ticketDao().insertTicket(ticket)
    
    suspend fun updateTicket(ticket: TicketEntity) = database.ticketDao().updateTicket(ticket)
    
    suspend fun deleteTicket(ticket: TicketEntity) = database.ticketDao().deleteTicket(ticket)
    
    suspend fun deleteTicketById(id: Long) = database.ticketDao().deleteTicketById(id)
    
    suspend fun getTicketCount(): Int = database.ticketDao().getTicketCount()
    
    // User operations
    fun getCurrentUser(): Flow<UserEntity?> = database.userDao().getCurrentUser()
    
    suspend fun getUserById(id: Long): UserEntity? = database.userDao().getUserById(id)
    
    /**
     * Получает текущего авторизованного пользователя по ID из AuthManager
     */
    suspend fun getAuthenticatedUser(userId: Long): UserEntity? = getUserById(userId)
    
    suspend fun getUserByEmail(email: String): UserEntity? = database.userDao().getUserByEmail(email)
    
    suspend fun insertUser(user: UserEntity): Long = database.userDao().insertUser(user)
    
    suspend fun updateUser(user: UserEntity) = database.userDao().updateUser(user)
    
    /**
     * Авторизация пользователя по email и паролю
     * @return UserEntity если авторизация успешна, null если неверные данные
     */
    suspend fun login(email: String, password: String): UserEntity? {
        val user = getUserByEmail(email)
        return if (user != null && user.password == password) {
            user
        } else {
            null
        }
    }
    
    /**
     * Регистрация нового пользователя
     * @return UserEntity если регистрация успешна, null если email уже существует
     */
    suspend fun register(name: String, email: String, phone: String, password: String): UserEntity? {
        // Проверяем, существует ли пользователь с таким email
        val existingUser = getUserByEmail(email)
        if (existingUser != null) {
            return null // Пользователь уже существует
        }
        
        val newUser = UserEntity(
            name = name,
            email = email,
            phone = phone,
            password = password
        )
        val userId = insertUser(newUser)
        return getUserById(userId)
    }
    
    // Search history operations
    fun getRecentSearches(limit: Int = 10): Flow<List<SearchHistoryEntity>> = 
        database.searchHistoryDao().getRecentSearches(limit)
    
    fun getAllSearches(): Flow<List<SearchHistoryEntity>> = database.searchHistoryDao().getAllSearches()
    
    suspend fun insertSearch(search: SearchHistoryEntity): Long = 
        database.searchHistoryDao().insertSearch(search)
    
    suspend fun deleteSearch(search: SearchHistoryEntity) = database.searchHistoryDao().deleteSearch(search)
    
    suspend fun deleteSearchById(id: Long) = database.searchHistoryDao().deleteSearchById(id)
    
    // Route operations
    fun getAllRoutes(): Flow<List<RouteEntity>> = database.routeDao().getAllRoutes()
    
    suspend fun getRouteById(id: Long): RouteEntity? = database.routeDao().getRouteById(id)
    
    /**
     * Поиск рейсов по маршруту (откуда и куда)
     */
    fun searchRoutes(from: String, to: String): Flow<List<RouteEntity>> = 
        database.routeDao().searchRoutes(from, to)
    
    /**
     * Поиск рейсов по маршруту и дате
     */
    fun searchRoutesByDate(from: String, to: String, date: String): Flow<List<RouteEntity>> = 
        database.routeDao().searchRoutesByDate(from, to, date)
    
    /**
     * Поиск рейсов по маршруту и диапазону дат
     */
    fun searchRoutesByDateRange(from: String, to: String, dateStart: String, dateEnd: String): Flow<List<RouteEntity>> = 
        database.routeDao().searchRoutesByDateRange(from, to, dateStart, dateEnd)
    
    suspend fun getAllFromCities(): List<String> = database.routeDao().getAllFromCities()
    
    suspend fun getAllToCities(): List<String> = database.routeDao().getAllToCities()
    
    suspend fun insertRoute(route: RouteEntity): Long = database.routeDao().insertRoute(route)
    
    suspend fun insertRoutes(routes: List<RouteEntity>) = database.routeDao().insertRoutes(routes)
    
    suspend fun updateRoute(route: RouteEntity) = database.routeDao().updateRoute(route)
    
    suspend fun deleteRoute(route: RouteEntity) = database.routeDao().deleteRoute(route)
    
    suspend fun getRouteCount(): Int = database.routeDao().getRouteCount()
}


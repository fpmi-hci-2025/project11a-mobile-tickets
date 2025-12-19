package com.example.tickets.data.dao

import androidx.room.*
import com.example.tickets.data.entity.RouteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes ORDER BY departureDate, departureTime")
    fun getAllRoutes(): Flow<List<RouteEntity>>

    @Query("SELECT * FROM routes WHERE id = :id")
    suspend fun getRouteById(id: Long): RouteEntity?

    /**
     * Поиск рейсов по маршруту (откуда и куда)
     */
    @Query("SELECT * FROM routes WHERE LOWER(`from`) LIKE LOWER('%' || :from || '%') AND LOWER(`to`) LIKE LOWER('%' || :to || '%') ORDER BY departureDate, departureTime")
    fun searchRoutes(from: String, to: String): Flow<List<RouteEntity>>

    /**
     * Поиск рейсов по маршруту и дате
     */
    @Query("SELECT * FROM routes WHERE LOWER(`from`) LIKE LOWER('%' || :from || '%') AND LOWER(`to`) LIKE LOWER('%' || :to || '%') AND departureDate = :date ORDER BY departureTime")
    fun searchRoutesByDate(from: String, to: String, date: String): Flow<List<RouteEntity>>
    
    /**
     * Поиск рейсов по маршруту и диапазону дат
     */
    @Query("SELECT * FROM routes WHERE LOWER(`from`) LIKE LOWER('%' || :from || '%') AND LOWER(`to`) LIKE LOWER('%' || :to || '%') AND departureDate >= :dateStart AND departureDate <= :dateEnd ORDER BY departureDate, departureTime")
    fun searchRoutesByDateRange(from: String, to: String, dateStart: String, dateEnd: String): Flow<List<RouteEntity>>

    /**
     * Получить все уникальные города отправления
     */
    @Query("SELECT DISTINCT `from` FROM routes ORDER BY `from`")
    suspend fun getAllFromCities(): List<String>

    /**
     * Получить все уникальные города назначения
     */
    @Query("SELECT DISTINCT `to` FROM routes ORDER BY `to`")
    suspend fun getAllToCities(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: RouteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutes(routes: List<RouteEntity>)

    @Update
    suspend fun updateRoute(route: RouteEntity)

    @Delete
    suspend fun deleteRoute(route: RouteEntity)

    @Query("DELETE FROM routes WHERE id = :id")
    suspend fun deleteRouteById(id: Long)

    @Query("DELETE FROM routes")
    suspend fun deleteAllRoutes()

    @Query("SELECT COUNT(*) FROM routes")
    suspend fun getRouteCount(): Int
}


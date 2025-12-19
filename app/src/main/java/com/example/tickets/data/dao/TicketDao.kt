package com.example.tickets.data.dao

import androidx.room.*
import com.example.tickets.data.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets ORDER BY purchaseDate DESC")
    fun getAllTickets(): Flow<List<TicketEntity>>

    @Query("SELECT * FROM tickets WHERE id = :id")
    suspend fun getTicketById(id: Long): TicketEntity?

    @Query("SELECT * FROM tickets WHERE status = :status ORDER BY purchaseDate DESC")
    fun getTicketsByStatus(status: String): Flow<List<TicketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickets(tickets: List<TicketEntity>)

    @Update
    suspend fun updateTicket(ticket: TicketEntity)

    @Delete
    suspend fun deleteTicket(ticket: TicketEntity)

    @Query("DELETE FROM tickets WHERE id = :id")
    suspend fun deleteTicketById(id: Long)

    @Query("DELETE FROM tickets")
    suspend fun deleteAllTickets()
    
    @Query("SELECT COUNT(*) FROM tickets")
    suspend fun getTicketCount(): Int
}


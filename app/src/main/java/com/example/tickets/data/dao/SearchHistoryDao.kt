package com.example.tickets.data.dao

import androidx.room.*
import com.example.tickets.data.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY searchDate DESC LIMIT :limit")
    fun getRecentSearches(limit: Int = 10): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_history ORDER BY searchDate DESC")
    fun getAllSearches(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchHistoryEntity): Long

    @Delete
    suspend fun deleteSearch(search: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE id = :id")
    suspend fun deleteSearchById(id: Long)

    @Query("DELETE FROM search_history")
    suspend fun deleteAllSearches()
}


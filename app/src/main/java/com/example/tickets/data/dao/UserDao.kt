package com.example.tickets.data.dao

import androidx.room.*
import com.example.tickets.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY createdAt DESC LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}


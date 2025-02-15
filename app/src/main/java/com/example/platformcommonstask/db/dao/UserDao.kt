package com.example.platformcommonstask.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.platformcommonstask.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE synced = 0")
    fun getUnsyncedUsers(): List<UserEntity>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>


    @Update
    suspend fun updateUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long
}
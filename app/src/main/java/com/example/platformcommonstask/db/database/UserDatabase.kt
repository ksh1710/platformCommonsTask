package com.example.platformcommonstask.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.platformcommonstask.db.dao.UserDao
import com.example.platformcommonstask.db.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
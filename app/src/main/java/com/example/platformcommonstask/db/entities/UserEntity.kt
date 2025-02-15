package com.example.platformcommonstask.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val job: String,
    val remoteId: String? = null,
    val synced: Boolean = false
)
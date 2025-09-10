package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phoneNumber: String
)

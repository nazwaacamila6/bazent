package com.example.bazent.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey
    val email: String,

    val fullName: String,
    val phoneNumber: String,
    val password: String
)
package com.example.bazent.data.local

import com.google.firebase.Timestamp
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_local")
data class EventEntity(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val city: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val userId: String = "",
    val status: String = "",

    val likes: Int = 0,

    val likedBy: List<String> = emptyList(),
    val joinedUser: List<String> = emptyList(),

    val maxParticipants: Int = 0,

    val createdAt: Timestamp? = null,
    val eventDate: Timestamp? = null
)

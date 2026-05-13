package com.example.bazent.data

import com.google.firebase.Timestamp

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val userId: String = "",
    val status: String = "",

    val likes: Int = 0,

    val likedBy: List<String> = emptyList(),
    val joinedUsers: List<String> = emptyList(),

    val maxParticipants: Int = 0,

    val createdAt: Timestamp? = null,
    val eventDate: Timestamp? = null
)

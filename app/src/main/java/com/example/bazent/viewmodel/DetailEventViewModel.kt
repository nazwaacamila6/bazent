package com.example.bazent.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bazent.data.EventEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DetailEventViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val currentUserId =
        FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid
            ?: ""

    var event =
        mutableStateOf<EventEntity?>(null)
        private set

    fun getEvent(eventId: String) {

        db.collection("Events")
            .document(eventId)
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                if (value != null && value.exists()) {

                    event.value =
                        value.toObject(EventEntity::class.java)?.copy(
                            id = value.id
                        )
                }
            }
    }

    fun joinEvent(eventId: String) {

        db.collection("Events")
            .document(eventId)
            .update(
                "joinedUser",
                FieldValue.arrayUnion(currentUserId)
            )
    }

    fun isJoined(event: EventEntity): Boolean {

        return event.joinedUsers.contains(currentUserId)
    }
}
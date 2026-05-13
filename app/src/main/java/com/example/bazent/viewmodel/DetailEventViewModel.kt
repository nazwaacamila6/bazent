package com.example.bazent.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bazent.data.Event
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
        mutableStateOf<Event?>(null)
        private set

    fun getEvent(eventId: String) {

        db.collection("events")
            .document(eventId)
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                if (value != null && value.exists()) {

                    event.value =
                        value.toObject(Event::class.java)?.copy(
                            id = value.id
                        )
                }
            }
    }

    fun joinEvent(eventId: String) {

        db.collection("events")
            .document(eventId)
            .update(
                "joinedUsers",
                FieldValue.arrayUnion(currentUserId)
            )
    }

    fun isJoined(event: Event): Boolean {

        return event.joinedUsers.contains(currentUserId)
    }
}
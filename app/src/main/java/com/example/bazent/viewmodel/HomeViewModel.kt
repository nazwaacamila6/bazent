package com.example.bazent.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bazent.data.Event
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val currentUserId =
        FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid
            ?: ""

    var events = mutableStateListOf<Event>()
        private set

    init {
        getEvents()
    }

    private fun getEvents() {

        db.collection("Events")
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                events.clear()

                value?.documents?.mapNotNull { doc ->

                    doc.toObject(Event::class.java)?.copy(
                        id = doc.id
                    )

                }?.let {
                    events.addAll(it)
                }
            }
    }

    fun toggleLike(event: Event) {

        val isLiked =
            event.likedBy.contains(currentUserId)

        val updatedLikedBy =
            if (isLiked) {
                event.likedBy - currentUserId
            } else {
                event.likedBy + currentUserId
            }

        val updatedLikes = updatedLikedBy.size

        db.collection("Events")
            .document(event.id)
            .update(
                mapOf(
                    "likes" to updatedLikes,
                    "likedBy" to updatedLikedBy
                )
            )
    }
}
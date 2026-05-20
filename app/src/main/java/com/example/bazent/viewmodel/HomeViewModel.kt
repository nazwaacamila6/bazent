package com.example.bazent.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bazent.data.local.EventEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val currentUserId =
        FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid
            ?: ""

    var selectedCity =""
    var cities = mutableStateListOf<String>()
        private set

    var events = mutableStateListOf<EventEntity>()
        private set

    init {
        getEvents()
    }

    private fun getEvents(city: String ="") {
        var query: Query = db.collection("Events")
        if (city.isNotEmpty()){
            query = query.whereEqualTo("city", city)
        }
            query.addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                events.clear()

                value?.documents?.mapNotNull { doc ->

                    doc.toObject(EventEntity::class.java)?.copy(
                        id = doc.id
                    )

                }?.let {
                    events.addAll(it)
                    cities.clear()
                    cities.add("All")
                    cities.addAll(
                        it.map { event ->
                            event.city
                        }
                            .filter { city ->
                                city.isNotBlank()
                            }.distinct()
                    )
                }
            }
    }

    fun toggleLike(event: EventEntity) {

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

    fun filterByCity(city: String) {
        selectedCity = city
        getEvents(city)
    }
}
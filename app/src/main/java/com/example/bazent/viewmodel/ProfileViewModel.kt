package com.example.bazent.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bazent.data.local.EventEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _fullName = MutableStateFlow("")
    val fullName : StateFlow<String> = _fullName

    private val _email = MutableStateFlow("")
    val email : StateFlow<String> = _email

    private val _sharedEvents = MutableStateFlow<List<EventEntity>>(emptyList())

    val sharedEvents : StateFlow<List<EventEntity>> = _sharedEvents

    private val _draftEvents = MutableStateFlow<List<EventEntity>>(emptyList())

    val draftEvents : StateFlow<List<EventEntity>> = _draftEvents

    private val _error = MutableStateFlow("")
    val error : StateFlow<String> = _error

    init {
        loadUserData()
        loadUserEvents()
    }

    private fun loadUserData() {
        val uid = auth.currentUser?.uid

        if (uid != null) {
            firestore.collection("users")
                .document(uid)
                .get()

                .addOnSuccessListener { document ->

                    _fullName.value = document.getString("fullName") ?: ""
                    _email.value = document.getString("email") ?: ""
                }
        }
    }

    private fun loadUserEvents() {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("Events")
            .whereEqualTo("userId", uid)
            .get()

            .addOnSuccessListener { result ->
                val allEvents = result.toObjects(EventEntity::class.java)

                _sharedEvents.value = allEvents.filter {
                    it.status == "shared"
                }

                _draftEvents.value = allEvents.filter {
                    it.status == "draft"

                }
            }
            .addOnFailureListener { exception ->
                _error.value =
                    exception.message ?: "Failed to load events"
            }
    }

    fun logout() {
        auth.signOut()
    }
}
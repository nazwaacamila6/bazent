package com.example.bazent.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bazent.data.local.AppDatabase
import com.example.bazent.data.local.UserEntity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _registerState = MutableStateFlow<String?>(null)
    val registerState : StateFlow<String?> = _registerState

    fun registerUser(
        email : String,
        fullName : String,
        phoneNumber : String,
        password : String,
        confirmPassword : String
    ) {
        when {

            email.isEmpty() ||
                    fullName.isEmpty() ||
                    phoneNumber.isEmpty() ||
                    password.isEmpty() ||
                    confirmPassword.isEmpty() -> {

                        _registerState.value = "All fields must be filled"
                    }

            password != confirmPassword -> {
                _registerState.value = "Password does not match"
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _registerState.value = "Invalid email format"
            }

            else -> {
                auth.createUserWithEmailAndPassword(email, password)

                    .addOnSuccessListener {
                        val uid = auth.currentUser?.uid

                        val userData = hashMapOf(
                            "email" to email,
                            "fullName" to fullName,
                            "phoneNumber" to phoneNumber,
                            "createdAt" to Timestamp.now()
                        )

                        if (uid != null) {
                            firestore.collection("users")
                                .document(uid)
                                .set(userData)

                                .addOnSuccessListener {
                                    viewModelScope.launch {
                                        userDao.insertUser(
                                            UserEntity(
                                                email = email,
                                                fullName = fullName,
                                                phoneNumber = phoneNumber,
                                                password = password
                                            )
                                        )

                                        _registerState.value =
                                            "SUCCESS"
                                    }
                                }

                                .addOnFailureListener {
                                    _registerState.value =
                                        "Failed to save user data"
                                }
                        }
                    }

                    .addOnFailureListener {
                        _registerState.value =
                            it.message ?: "Register failed"
                    }
            }

        }
    }

    fun clearState() {
        _registerState.value = null
    }
}
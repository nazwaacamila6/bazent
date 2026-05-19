package com.example.bazent.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bazent.data.local.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()
    private val dbRoom = AppDatabase.getDatabase(application)
    private val userDao = dbRoom.userDao()

    fun onLoginClick(onSuccess: () -> Unit) {
        val context = getApplication<Application>().applicationContext

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return
        }

        isLoading = true

        // Coba Login Firebase (Online)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoading = false
                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener {
                // Jika Gagal Online, Coba Room (Offline)
                viewModelScope.launch {
                    val localUser = userDao.login(email, password)
                    isLoading = false
                    if (localUser != null) {
                        Toast.makeText(context, "Login Offline Success", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    } else {
                        Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
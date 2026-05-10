package com.example.bazent.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bazent.ui.theme.DarkBlue
import com.example.bazent.ui.theme.LightBlue
import com.example.bazent.ui.theme.PrimaryBlue
import com.example.bazent.ui.theme.SoftBlue
import com.example.bazent.ui.theme.TextGray
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.bazent.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp

@Composable
fun RegisterScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SoftBlue,
                        LightBlue
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_bazent),
                contentDescription = "Logo Bazent",

                modifier = Modifier
                    .size(160.dp)
                    .padding(start = 8.dp),

                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(20.dp))

            // SUBTITLE
            Text(
                text = "Temukan event gratis, gabung, dan terhubung bersama.",
                fontSize = 16.sp,
                color = TextGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            // CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Dive into the Circle",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Join a community of authentic explorers.",
                        fontSize = 15.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // EMAIL
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "EMAIL",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },

                            modifier = Modifier.fillMaxWidth(),

                            placeholder = {
                                Text("Enter email")
                            },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),

                            shape = RoundedCornerShape(18.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = Color.LightGray,

                                focusedTextColor = DarkBlue,
                                unfocusedTextColor = DarkBlue,

                                cursorColor = PrimaryBlue,

                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,

                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray
                            ),

                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // FULL NAME
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "FULL NAME",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = fullName,
                            onValueChange = {
                                fullName = it
                            },

                            modifier = Modifier.fillMaxWidth(),

                            placeholder = {
                                Text("Enter full name")
                            },

                            shape = RoundedCornerShape(18.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = Color.LightGray,

                                focusedTextColor = DarkBlue,
                                unfocusedTextColor = DarkBlue,

                                cursorColor = PrimaryBlue,

                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,

                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray
                            ),

                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // PHONE NUMBER
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "PHONE NUMBER",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = {
                                phoneNumber = it
                            },

                            modifier = Modifier.fillMaxWidth(),

                            placeholder = {
                                Text("Enter phone number")
                            },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),

                            shape = RoundedCornerShape(18.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = Color.LightGray,

                                focusedTextColor = DarkBlue,
                                unfocusedTextColor = DarkBlue,

                                cursorColor = PrimaryBlue,

                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,

                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray
                            ),

                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // PASSWORD
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "PASSWORD",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                            },

                            modifier = Modifier.fillMaxWidth(),

                            placeholder = {
                                Text("Enter password")
                            },

                            visualTransformation =
                                if (passwordVisible) VisualTransformation.None
                                else PasswordVisualTransformation(),

                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }) {
                                    Icon(
                                        imageVector =
                                            if (passwordVisible)
                                                Icons.Default.Visibility
                                            else
                                                Icons.Default.VisibilityOff,

                                        contentDescription = "Toggle Password"
                                    )
                                }
                            },

                            shape = RoundedCornerShape(18.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = Color.LightGray,

                                focusedTextColor = DarkBlue,
                                unfocusedTextColor = DarkBlue,

                                cursorColor = PrimaryBlue,

                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,

                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray
                            ),

                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // CONFIRM PASSWORD
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "CONFIRM PASSWORD",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = {
                                confirmPassword = it
                            },

                            visualTransformation =
                                if (confirmPasswordVisible) VisualTransformation.None
                                else PasswordVisualTransformation(),

                            trailingIcon = {
                                IconButton(onClick = {
                                    confirmPasswordVisible = !confirmPasswordVisible
                                }) {
                                    Icon(
                                        imageVector =
                                            if (confirmPasswordVisible)
                                                Icons.Default.Visibility
                                            else
                                                Icons.Default.VisibilityOff,

                                        contentDescription = "Toggle Password"
                                    )
                                }
                            },

                            modifier = Modifier.fillMaxWidth(),

                            placeholder = {
                                Text("Confirm password")
                            },

                            shape = RoundedCornerShape(18.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = Color.LightGray,

                                focusedTextColor = DarkBlue,
                                unfocusedTextColor = DarkBlue,

                                cursorColor = PrimaryBlue,

                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,

                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray
                            ),

                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(36.dp))

                    // BUTTON
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .background(
                                color = DarkBlue,
                                shape = RoundedCornerShape(18.dp)
                            ).clickable {

                                if (email.isEmpty() ||
                                    fullName.isEmpty() ||
                                    phoneNumber.isEmpty() ||
                                    password.isEmpty() ||
                                    confirmPassword.isEmpty()
                                ) {

                                    Toast.makeText(
                                        context,
                                        "All fields must be filled",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else if (password != confirmPassword) {

                                    Toast.makeText(
                                        context,
                                        "Password does not match",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                                        Toast.makeText(
                                            context,
                                            "Invalid email format",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    } else {

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

                                                db.collection("users")
                                                    .document(uid)
                                                    .set(userData)

                                                    .addOnSuccessListener {

                                                        Toast.makeText(
                                                            context,
                                                            "Register Success",
                                                            Toast.LENGTH_SHORT
                                                        ).show()

                                                        navController.navigate("home")
                                                    }

                                                    .addOnFailureListener {

                                                        Toast.makeText(
                                                            context,
                                                            "Failed to save user data",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            }
                                        }

                                        .addOnFailureListener {

                                            Toast.makeText(
                                                context,
                                                it.message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }

                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Create Account",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // SIGN IN
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Already have an account? ",
                            color = TextGray
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Sign in here",
                            color = Color(0xFFF2C94C),
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.clickable {

                                navController.navigate("login")

                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // TERMS
            Text(
                text = "By creating an account, you agree to Bazent's Terms of Service and Privacy Policy.",
                fontSize = 12.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
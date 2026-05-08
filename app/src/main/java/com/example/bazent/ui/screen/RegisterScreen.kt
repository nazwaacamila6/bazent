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
import com.example.bazent.ui.theme.CardWhite
import com.example.bazent.ui.theme.DarkBlue
import com.example.bazent.ui.theme.LightBlue
import com.example.bazent.ui.theme.PrimaryBlue
import com.example.bazent.ui.theme.SoftBlue
import com.example.bazent.ui.theme.TextGray
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.bazent.R

@Composable
fun RegisterScreen(
    navController: NavController
) {

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
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(90.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_bazent),
                contentDescription = "Logo Bazent",

                modifier = Modifier
                    .size(140.dp),

                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // SUBTITLE
            Text(
                text = "Temukan event gratis, gabung, dan terhubung bersama.",
                fontSize = 16.sp,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(50.dp))

            // CARD
            Card(
                shape = RoundedCornerShape(36.dp),
                modifier = Modifier.fillMaxWidth()
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
                        color = TextGray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // USERNAME
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "USERNAME",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .background(
                                    color = CardWhite,
                                    shape = RoundedCornerShape(18.dp)
                                )
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

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .background(
                                    color = CardWhite,
                                    shape = RoundedCornerShape(18.dp)
                                )
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

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .background(
                                    color = CardWhite,
                                    shape = RoundedCornerShape(18.dp)
                                )
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
                            ),

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
                    Row {

                        Text(
                            text = "Already have an account? ",
                            color = TextGray
                        )

                        Text(
                            text = "Sign in here",
                            color = Color(0xFFF2C94C),
                            fontWeight = FontWeight.Bold
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
                color = TextGray
            )
        }
    }
}
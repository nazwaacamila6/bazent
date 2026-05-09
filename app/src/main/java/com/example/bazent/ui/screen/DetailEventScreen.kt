package com.example.bazent.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bazent.R
import com.example.bazent.ui.theme.*
import androidx.compose.runtime.*

@Composable
fun DetailEventScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 150.dp)
        ) {

            item {

                // HEADER
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CardWhite,
                    shadowElevation = 8.dp,
                    shape = RoundedCornerShape(
                        bottomStart = 28.dp,
                        bottomEnd = 28.dp
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 24.dp,
                                vertical = 18.dp
                            ),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.logo_bazent),
                            contentDescription = null,
                            modifier = Modifier.size(55.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,

                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape),

                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // CONTENT
                Column(
                    modifier = Modifier.padding(horizontal = 22.dp)
                ) {

                    // IMAGE EVENT
                    Card(
                        shape = RoundedCornerShape(28.dp)
                    ) {

                        Box {

                            Image(
                                painter = painterResource(id = R.drawable.music),
                                contentDescription = null,

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(430.dp),

                                contentScale = ContentScale.Crop
                            )

                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFFD400))
                                    .padding(
                                        horizontal = 14.dp,
                                        vertical = 6.dp
                                    )
                            ) {

                                Text(
                                    text = "FREE",
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryBlue
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // TITLE
                    Text(
                        text = "Acoustic Garden\nNight",
                        fontSize = 28.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "SATURDAY, OCT 26 • 7:00 PM",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // DESCRIPTION CARD
                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(26.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = CardWhite
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(26.dp)
                        ) {

                            Text(
                                text = "Nikmati malam santai dengan live music akustik bareng teman teman. Suasana hangat, lampu gantung, dan lagu - lagu santai bikin momen nongkrong makin seru.",

                                fontSize = 16.sp,
                                lineHeight = 30.sp,
                                color = TextGray
                            )

                            Spacer(modifier = Modifier.height(26.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFBFEAF3),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .padding(16.dp)
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = PrimaryBlue
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = "Twin House Cipete, Jakarta Selatan (Outdoor Area)",
                                        color = TextGray,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // PARTICIPANTS
                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(26.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = CardWhite
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(22.dp)
                        ) {

                            Text(
                                text = "Participants (42)",
                                color = DarkBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            var showAllParticipants by remember {
                                mutableStateOf(false)
                            }

                            val allParticipants = listOf(
                                "Nazwa Camila",
                                "Cina Brelian",
                                "Lian Anugrah Oktaviani",
                                "Aulia Surya Nugraheni",
                                "Surya Saputra",
                                "Aditya Pratama",
                                "Dimas Saputro",
                                "Rizky Ramadhan",
                                "Putri Maharani",
                                "Anisa Putri",
                                "Kevin Wijaya",
                                "Bagas Prakoso",
                                "Farel Akbar",
                                "Mutiara Nabila",
                                "Raka Mahendra",
                                "Nadya Permata"
                            )

                            val displayedParticipants =
                                if (showAllParticipants) {
                                    allParticipants
                                } else {
                                    allParticipants.take(6)
                                }

                            displayedParticipants.forEach {

                                Text(
                                    text = it,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text =
                                    if (showAllParticipants)
                                        "Show Less"
                                    else
                                        "Selanjutnya",

                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold,

                                modifier = Modifier
                                    .align(Alignment.End)
                                    .clickable {

                                        showAllParticipants =
                                            !showAllParticipants
                                    }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // BUTTON
                    Button(
                        onClick = { },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),

                        shape = RoundedCornerShape(50.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {

                        Text(
                            text = "Join This Event",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // BOTTOM NAVBAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {

            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 10.dp
                    )
                    .navigationBarsPadding()
                    .height(72.dp)
                    .clip(RoundedCornerShape(28.dp)),

                containerColor = CardWhite,
                tonalElevation = 8.dp
            ) {

                NavigationBarItem(
                    selected = true,
                    onClick = {
                        navController.navigate("home")
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("HOME")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },

                    icon = {}
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },

                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("PROFILE")
                    }
                )
            }

            FloatingActionButton(
                onClick = { },

                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-22).dp)
                    .size(68.dp),

                containerColor = DarkBlue
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }
}
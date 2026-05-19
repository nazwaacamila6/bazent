package com.example.bazent.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.bazent.viewmodel.DetailEventViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailEventScreen(
    navController: NavController,
    eventId: String,
    viewModel: DetailEventViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getEvent(eventId)
    }

    val currentEvent = viewModel.event.value ?: return

    val formattedDate =
        currentEvent.eventDate?.toDate()?.let {
            SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(it)
        } ?: ""

val isJoined =
    viewModel.isJoined(currentEvent)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .statusBarsPadding()
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 150.dp)
        ) {

            item {

                // HEADER
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp),

                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    ),

                    colors = CardDefaults.cardColors(
                        containerColor = SoftBlue
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 10.dp,
                                bottom = 18.dp
                            ),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.logo_bazent),
                            contentDescription = null,
                            modifier = Modifier.size(55.dp)
                        )

                        Box {

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

                            AsyncImage(
                                model = currentEvent.imageUrl,
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
                        text = currentEvent.title,
                        fontSize = 28.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )

                    Spacer(modifier = Modifier.height(16.dp))

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
                            text = formattedDate,
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
                                text = currentEvent.description,

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
                                        text = currentEvent.location,
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
                                text = "Participants (${currentEvent.joinedUser.size})",
                                color = DarkBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // BUTTON
                    Button(
                        onClick = {

                            viewModel.joinEvent(currentEvent.id)
                        },

                        enabled = !isJoined,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),

                        shape = RoundedCornerShape(50.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {

                        Text(
                            text =
                                if (isJoined)
                                    "Already Joined"
                                else
                                    "Join This Event",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
}
package com.example.bazent.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bazent.R
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import com.example.bazent.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

data class Event(
    val title: String,
    val date: String,
    val location: String,
    val image: Int
)

@Composable
fun HomeScreen(navController: NavController) {

    val events = listOf(

        Event(
            title = "Green Community Walk",
            date = "Oct 24, 5:00 PM",
            location = "Jakarta Selatan",
            image = R.drawable.menanam
        ),

        Event(
            title = "Acoustic Garden Night",
            date = "Oct 36, 7:00 PM",
            location = "Cipete Jakarta Selatan",
            image = R.drawable.music
        ),

        Event(
            title = "Library Study Club",
            date = "Oct 27, 6:30 AM",
            location = "Jakarta Pusat",
            image = R.drawable.belajar
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(60.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.logo_bazent),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "CIRCLO",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Discover Events",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Find your next circle and connect.",
                    color = TextGray,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            items(events) { event ->

                EventCard(event)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {

            Card(
                shape = RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor = CardWhite
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(70.dp))

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = TextGray,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            FloatingActionButton(
                onClick = { },

                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-25).dp),

                containerColor = PrimaryBlue
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardWhite
        )
    ) {
        Column {
            Image(
                painter = painterResource(id = event.image),
                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = event.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = event.date,
                    color = TextGray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = event.location,
                    color = TextGray
                )
            }
        }
    }
}

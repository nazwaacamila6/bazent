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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*

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
                .fillMaxSize(),

            contentPadding = PaddingValues(
                bottom = 140.dp
            )
        ) {

            item {

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
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
                                top = 50.dp,
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

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {

                    Spacer(modifier = Modifier.height(24.dp))
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

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            items(events) { event ->

                Box(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    EventCard(
                        event = event,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    navController: NavController
) {

    var isLiked by remember{
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable {
                navController.navigate("detail_event")
            },
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardWhite
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        IconButton(
                            onClick = {
                                isLiked =!isLiked
                            }
                        ) {
                            Icon(
                                imageVector =
                                if (isLiked)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint =
                                    if (isLiked)
                                        Color.Red
                                else
                                        PrimaryBlue
                            )
                        }

                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = PrimaryBlue
                            )
                        }
                    }
                }

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

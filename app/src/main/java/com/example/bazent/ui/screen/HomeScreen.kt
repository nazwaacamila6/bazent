package com.example.bazent.ui.screen

import com.example.bazent.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Favorite
import com.example.bazent.data.local.EventEntity
import java.text.SimpleDateFormat
import java.util.Locale
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {

    val events = homeViewModel.events

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .statusBarsPadding()
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
                                contentDescription = "Profile",

                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape),

                                contentScale = ContentScale.Crop
                            )

                        }
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
                        navController = navController,
                        onLikeClick = {
                            homeViewModel.toggleLike(event)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: EventEntity,
    navController: NavController,
    onLikeClick: () -> Unit
) {

    val context = LocalContext.current

    val currentUserId =
        FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid
            ?: ""

    val isLiked =
        event.likedBy.contains(currentUserId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable {
                navController.navigate("detail_event/${event.id}")
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
            AsyncImage(
                model = event.imageUrl,
                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),

                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                val formattedDate =
                    event.eventDate?.toDate()?.let {
                        SimpleDateFormat(
                            "dd MMM yyyy",
                            Locale.getDefault()
                        ).format(it)
                    } ?: ""

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
                                onLikeClick()
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
                            onClick = {

                                val shareText = """
                                    🎉 Join this event on Bazent!
                                    
                                    ${event.title}
                                    
                                    📅 Date: $formattedDate
                                    📍 Location: ${event.location}
                                    
                                    Don't miss it 🚀
                                    
                                    Download Bazent and discover more events!
                                """.trimIndent()

                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                    type = "text/plain"
                                }

                                val shareIntent =
                                    Intent.createChooser(sendIntent, "Share Event")

                                context.startActivity(shareIntent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = PrimaryBlue
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${event.likes} likes",
                    color = TextGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = formattedDate,
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

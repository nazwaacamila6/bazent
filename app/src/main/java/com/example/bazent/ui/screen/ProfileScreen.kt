package com.example.bazent.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bazent.R
import com.example.bazent.ui.theme.DarkBlue
import com.example.bazent.ui.theme.LightBlue
import com.example.bazent.ui.theme.PrimaryBlue
import com.example.bazent.ui.theme.SoftBlue
import com.example.bazent.ui.theme.TextGray
import com.example.bazent.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val viewModel : ProfileViewModel = viewModel()

    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()

    var selectedTab by remember {
        mutableStateOf("shared")
    }

    val sharedEvents by viewModel.sharedEvents.collectAsState()

    val draftEvents by viewModel.draftEvents.collectAsState()

    val formatter = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )

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
            .statusBarsPadding()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),

            contentPadding = PaddingValues(
                bottom = 140.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                // TOP BAR
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


                Spacer(modifier = Modifier.height(30.dp))

                // PROFILE IMAGE
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",

                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(40.dp)),

                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                // USERNAME
                Text(
                    text = fullName,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )

                // EMAIL
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = email,
                    color = TextGray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                // TABS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    // SHARED TAB
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (selectedTab == "shared")
                                    DarkBlue
                                else
                                    Color.White
                            )
                            .clickable {
                                selectedTab = "shared"
                            }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            )
                    ) {

                        Text(
                            text = "Shared Events",
                            color =
                                if (selectedTab == "shared")
                                    Color.White
                                else
                                    DarkBlue,

                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // DRAFT TAB
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (selectedTab == "draft")
                                    DarkBlue
                                else
                                    Color.White
                            )
                            .clickable {
                                selectedTab = "draft"
                            }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            )
                    ) {

                        Text(
                            text = "Draft Events",
                            color =
                                if (selectedTab == "draft")
                                    Color.White
                                else
                                    DarkBlue,

                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }

            if (
                (selectedTab == "shared" && sharedEvents.isEmpty()) ||
                (selectedTab == "draft" && draftEvents.isEmpty())
            ) {

                item {

                    Text(
                        text =
                            if (selectedTab == "shared")
                                "No shared events yet"
                            else
                                "No draft events yet",

                        color = TextGray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            }

            // EVENT LIST
            items(
                if (selectedTab == "shared")
                    sharedEvents
                else
                    draftEvents
            ) { event ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 18.dp),

                    shape = RoundedCornerShape(28.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = event.title,

                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(20.dp)),

                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    if (selectedTab == "draft")
                                        "BELUM DIUPLOAD"
                                    else
                                        "SUDAH DIUPLOAD",

                                color = Color(0xFFF2C94C),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = event.title,
                                fontSize = 20.sp,
                                color = DarkBlue,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${event.location} • ${
                                    event.eventDate?.toDate()?.let {
                                        formatter.format(it)
                                    } ?: ""
                                }",
                                color = TextGray,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(PrimaryBlue),

                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text =
                                        if (selectedTab == "draft")
                                            "Upload Now"
                                        else
                                            "View Event",

                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                        viewModel.logout()

                        navController.navigate("login") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(52.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFE5E5)
                    ),

                    shape = RoundedCornerShape(50.dp),

                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    ),

                    contentPadding = PaddingValues(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Logout",
                        color = Color(0xFFE53935),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
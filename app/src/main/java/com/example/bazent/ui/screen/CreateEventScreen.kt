package com.example.bazent.ui.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bazent.R
import com.example.bazent.ui.theme.*
import com.example.bazent.viewmodel.CreateEventViewModel
import java.util.Calendar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    viewModel: CreateEventViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(Unit) {
        viewModel.checkInternetConnection()
    }

    // State untuk mengontrol pop-up DatePicker Material 3
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Setup TimePickerDialog bawaan Android
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> viewModel.updateTime(hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.imageUrl = uri.toString()
            println("Log: Foto berhasil dipilih -> $uri")
        }
    }

    Scaffold(
        containerColor = SoftBlue,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_bazent),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create New Event",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gather your circle and make waves in the community.",
                fontSize = 14.sp,
                color = TextGray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Box Upload Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.5f))
                    .border(1.dp, PrimaryBlue.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .clickable {
                        // Membuka galeri sistem HP khusus untuk gambar
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                // Jika user sudah memilih gambar, tampilkan preview gambarnya full di dalam Box
                if (viewModel.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = viewModel.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Jika belum milih gambar, tampilkan teks dan ikon bawaan
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(40.dp)
                        )
                        Text("Upload Event Cover", color = DarkBlue, fontWeight = FontWeight.SemiBold)
                        Text("Recommended: 1200x600px", color = TextGray, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                label = "Event Title",
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                placeholder = "What are we doing?"
            )

            // --- BAGIAN DATE & TIME YANG SUDAH PAKAI PICKER ---
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Kolom Tanggal
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePicker = true }
                ) {
                    CustomTextField(
                        label = "Date",
                        value = viewModel.date,
                        onValueChange = {},
                        placeholder = "Select Date",
                        isClickableOnly = true // Mengunci keyboard mengetik
                    )
                }
                // Kolom Jam
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { timePickerDialog.show() }
                ) {
                    CustomTextField(
                        label = "Time",
                        value = viewModel.time,
                        onValueChange = {},
                        placeholder = "Select Time",
                        isClickableOnly = true // Mengunci keyboard mengetik
                    )
                }
            }

            CustomTextField(
                label = "Location",
                value = viewModel.location,
                onValueChange = { viewModel.location = it },
                placeholder = "Add a place",
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryBlue) }
            )

            CustomTextField(
                label = "Description",
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                placeholder = "Tell everyone more about the event...",
                singleLine = false,
                modifier = Modifier.height(120.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        // Panggil fungsi draft dan suruh navController buat balik halaman pas sukses
                        viewModel.saveToDraft(onSuccess = {
                            navController.popBackStack()
                        })
                    },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Draft", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        viewModel.createEvent(onSuccess = {
                            navController.popBackStack()
                        })
                    },
                    // KUNCI: Tombol aktif jika judul terisi DAN HP sedang online
                    enabled = viewModel.title.isNotEmpty() && viewModel.isDeviceOnline,
                    modifier = Modifier.weight(1.2f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        disabledContainerColor = Color.LightGray // Berubah abu-abu kalau offline
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Teks berubah dinamis mengikuti status internet
                        Text(
                            text = if (viewModel.isDeviceOnline) "Create Event" else "Offline Mode",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDeviceOnline) Color.White else Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (viewModel.isDeviceOnline) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                        }
                    }
                }
            }

            // --- DIALOG POP-UP DATE PICKER MATERIAL 3 ---
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { viewModel.updateDate(it) }
                            showDatePicker = false
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    isClickableOnly: Boolean = false, // Parameter baru untuk mendeteksi mode picker
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, color = DarkBlue, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = isClickableOnly, // Jika ini true, keyboard gak bakal muncul
            enabled = !isClickableOnly,  // Menonaktifkan fokus ketikan manual
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, Color.White, RoundedCornerShape(16.dp)),
            placeholder = { Text(placeholder, color = Color.LightGray) },
            leadingIcon = leadingIcon,
            singleLine = singleLine,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                // Supaya warna teksnya tetap hitam pekat/jelas meskipun kita set enabled = false
                disabledTextColor = LocalContentColor.current,
                disabledPlaceholderColor = Color.LightGray
            ),
            shape = RoundedCornerShape(16.dp)
        )
    }
}
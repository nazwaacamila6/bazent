package com.example.bazent.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bazent.data.local.AppDatabase
import com.example.bazent.data.local.EventEntity
import kotlinx.coroutines.launch
import java.util.UUID
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore // <--- IMPORT BARU
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import android.content.Context

class CreateEventViewModel(application: Application) : AndroidViewModel(application) {

    // Hubungkan ViewModel ini dengan database lokal Room kamu
    private val roomDb = AppDatabase.getDatabase(application)
    // 1. State untuk menampung inputan user (Pencatat Sementara)
    var title by mutableStateOf("")
    var date by mutableStateOf("") // Tampilan String di form (cth: 19 May 2026)
    var time by mutableStateOf("") // Tampilan String di form (cth: 10:30)
    var city by mutableStateOf("")
    var location by mutableStateOf("")
    var description by mutableStateOf("")

    // Opsional: Jika kamu ingin menambah field lain sesuai Data Class Event
    var imageUrl by mutableStateOf("")
    var maxParticipants by mutableStateOf(0)

    // State tambahan untuk mendeteksi apakah proses upload sedang berjalan atau tidak
    var isLoading by mutableStateOf(false)

    // Variabel internal untuk menyimpan objek Date asli hasil gabungan picker (untuk Firebase)
    private var selectedCalendar = Calendar.getInstance()

    // Instance Firebase Auth dan Firestore
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance() // <--- INSTANCE INSTANTIATION BARU

    // Fungsi tambahan untuk meng-update tanggal dari DatePicker di Screen
    fun updateDate(millis: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        selectedCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        selectedCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        selectedCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

        // Mengubah milidetik kalender menjadi String teks manis di form
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        date = sdf.format(selectedCalendar.time)
    }

    // Fungsi tambahan untuk meng-update jam dari TimePicker di Screen
    fun updateTime(hour: Int, minute: Int) {
        selectedCalendar.set(Calendar.HOUR_OF_DAY, hour)
        selectedCalendar.set(Calendar.MINUTE, minute)

        // Mengubah angka jam & menit menjadi format string jam "00:00"
        time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
    }

    // 2. Fungsi untuk handle klik tombol Create dan menyimpannya ke Firestore
    var isDeviceOnline by mutableStateOf(true)
    fun createEvent(onSuccess: () -> Unit) {
        val currentUserId = auth.currentUser?.uid ?: ""

        // Membuka jalan/referensi dokumen baru di dalam collection "Events" secara otomatis
        val eventRef = db.collection("Events").document()

        // Membungkus data ke dalam format Data Class Event untuk Firebase
        val newEvent = EventEntity(
            id = eventRef.id, // <--- Memasukkan id otomatis bawaan Firestore tadi ke dalam objek Event
            title = title,
            description = description,
            city = city,
            location = location,
            userId = currentUserId,
            imageUrl = imageUrl, // Sementara kosong/default
            status = "shared",
            maxParticipants = maxParticipants,
            createdAt = Timestamp(Date()), // Waktu pembuatan sekarang
            eventDate = Timestamp(selectedCalendar.time), // Menggunakan tanggal & jam asli pilihan user
            likedBy = emptyList(),
            joinedUser = emptyList(),
            likes = 0
        )

        isLoading = true // Mulai loading proses upload

        // Eksekusi penyimpanan data asli ke Firestore
        eventRef.set(newEvent)
            .addOnSuccessListener {
                println("Log: [Step 1/2] Sukses upload ke Firebase Firestore!")

                viewModelScope.launch {
                    try {
                        roomDb.eventDao().insertDraft(newEvent)
                        println("Log: [Step 2/2] Sukses menduplikat data ke Room Lokal!")
                        isLoading = false
                        clearFields()
                        onSuccess()
                    } catch (e: Exception) {
                        isLoading = false
                        println("Log: Gagal kloning ke lokal Room, tapi data di Firebase aman: ${e.message}")
                        clearFields()
                        onSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->
                isLoading = false
                println("Log: Gagal upload ke Firebase: ${e.message}")
            }
    }

    // 3. Fungsi untuk handle Draft
    // 3. Fungsi untuk handle Draft dan menyimpannya ke Room Database (OFFLINE)
    fun saveToDraft(onSuccess: () -> Unit) {
        val currentUserId = auth.currentUser?.uid ?: ""

        // Membuat ID unik sementara khusus untuk draft lokal HP kamu
        val localDraftId = "draft_${UUID.randomUUID()}"

        // Bungkus semua inputan form ke dalam objek EventEntity
        val draftEvent = EventEntity(
            id = localDraftId,
            title = title,
            description = description,
            city = city,
            location = location,
            userId = currentUserId,
            imageUrl = imageUrl,
            status = "draft", // <--- KUNCI: Kita tandai statusnya sebagai "draft"
            maxParticipants = maxParticipants,
            createdAt = Timestamp(Date()),
            eventDate = Timestamp(selectedCalendar.time),
            likedBy = emptyList(),
            joinedUser = emptyList(),
            likes = 0
        )

        // Room wajib dijalankan di background thread (Coroutine), makanya dibungkus viewModelScope
        viewModelScope.launch {
            try {
                // Perintah memasukkan data ke tabel lokal lewat EventDao yang kamu buat tadi
                roomDb.eventDao().insertDraft(draftEvent)
                println("Log: Tersimpan di draft lokal Room: ${draftEvent.title}")

                clearFields() // Bersihkan form biar kosong kembali
                onSuccess()   // Beri sinyal ke Screen agar menutup halaman form
            } catch (e: Exception) {
                println("Log: Gagal menyimpan draf ke lokal: ${e.message}")
            }
        }
    }

    // 4. Fungsi reset fields (biasanya dipakai setelah sukses simpan)
    fun clearFields() {
        title = ""
        date = ""
        time = ""
        location = ""
        city =""
        description = ""
        imageUrl = ""
        maxParticipants = 0
        selectedCalendar = Calendar.getInstance() // Reset kalender ke waktu sekarang
    }

    fun checkInternetConnection(){
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val network = connectivityManager.activeNetwork
        if (network == null) {
            isDeviceOnline = false
            return
        }
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        isDeviceOnline = capabilities != null && (
                capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                )
    }
}
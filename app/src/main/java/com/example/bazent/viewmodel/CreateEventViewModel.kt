package com.example.bazent.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bazent.data.Event
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore // <--- IMPORT BARU
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateEventViewModel : ViewModel() {

    // 1. State untuk menampung inputan user (Pencatat Sementara)
    var title by mutableStateOf("")
    var date by mutableStateOf("") // Tampilan String di form (cth: 19 May 2026)
    var time by mutableStateOf("") // Tampilan String di form (cth: 10:30)
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
    fun createEvent(onSuccess: () -> Unit) {
        val currentUserId = auth.currentUser?.uid ?: ""

        // Membuka jalan/referensi dokumen baru di dalam collection "Events" secara otomatis
        val eventRef = db.collection("Events").document()

        // Membungkus data ke dalam format Data Class Event untuk Firebase
        val newEvent = Event(
            id = eventRef.id, // <--- Memasukkan id otomatis bawaan Firestore tadi ke dalam objek Event
            title = title,
            description = description,
            location = location,
            userId = currentUserId,
            imageUrl = imageUrl, // Sementara kosong/default
            status = "active",
            maxParticipants = maxParticipants,
            createdAt = Timestamp(Date()), // Waktu pembuatan sekarang
            eventDate = Timestamp(selectedCalendar.time), // Menggunakan tanggal & jam asli pilihan user
            likedBy = emptyList(),
            joinedUsers = emptyList(),
            likes = 0
        )

        isLoading = true // Mulai loading proses upload

        // Eksekusi penyimpanan data asli ke Firestore
        eventRef.set(newEvent)
            .addOnSuccessListener {
                isLoading = false
                println("Log: Berhasil menyimpan data ke Firestore -> ${eventRef.id}")
                clearFields() // Reset form biar kosong kembali
                onSuccess()   // Pindah ke halaman beranda lewat Screen
            }
            .addOnFailureListener { e ->
                isLoading = false
                println("Log: Gagal menyimpan data ke Firestore: ${e.message}")
            }
    }

    // 3. Fungsi untuk handle Draft
    fun saveToDraft() {
        println("Log: Tersimpan di draft lokal: $title")
    }

    // 4. Fungsi reset fields (biasanya dipakai setelah sukses simpan)
    fun clearFields() {
        title = ""
        date = ""
        time = ""
        location = ""
        description = ""
        imageUrl = ""
        maxParticipants = 0
        selectedCalendar = Calendar.getInstance() // Reset kalender ke waktu sekarang
    }
}
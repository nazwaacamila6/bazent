package com.example.bazent.data.local

import androidx.room.*

@Dao
interface EventDao {

    // 1. Fungsi untuk menyimpan atau meng-update draf event ke dalam lokal HP
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(event: EventEntity)

    // 2. Fungsi untuk mengambil semua daftar draf event milik user yang sedang login
    @Query("SELECT * FROM events_local WHERE userId = :currentUserId")
    suspend fun getAllDrafts(currentUserId: String): List<EventEntity>

    // 3. Fungsi untuk menghapus draf dari lokal HP (biasanya setelah sukses di-upload ke Firebase)
    @Query("DELETE FROM events_local WHERE id = :eventId")
    suspend fun deleteDraft(eventId: String)
}
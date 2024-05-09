package org.d3if3162.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long =0L,
    val judul: String,
    val catatan: String,
    val tanggal: String
)

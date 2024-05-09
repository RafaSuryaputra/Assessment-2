package org.d3if3162.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan")
data class Karyawan(
    @PrimaryKey(autoGenerate = true)
    val id: Long =0L,
    val nama: String,
    val data_diri: String,
    val tanggal: String,
    val absen: String,
)

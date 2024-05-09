package org.d3if3162.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3162.app.model.Karyawan

@Dao
interface
    KaryawanDao {
    @Insert
    suspend fun insert(karyawan: Karyawan)

    @Update
    suspend fun update(karyawan: Karyawan)

    @Query("SELECT * FROM catatan ORDER BY nama,data_diri,tanggal ASC")
    fun getKaryawan(): Flow<List<Karyawan>>

    @Query("SELECT * FROM catatan WHERE id = :id")
    suspend fun getKaryawanById(id: Long): Karyawan?

    @Query("DELETE FROM catatan WHERE id = :id")
    suspend fun deleteById(id: Long)
}
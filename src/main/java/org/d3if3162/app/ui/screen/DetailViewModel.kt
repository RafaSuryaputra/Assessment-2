package org.d3if3162.app.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3162.app.database.KaryawanDao
import org.d3if3162.app.model.Karyawan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: KaryawanDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(judul: String, isi: String, absen: String) {
        val karyawan = Karyawan(
            tanggal = formatter.format(Date()),
            nama = judul,
            data_diri = isi,
            absen = absen,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(karyawan)
        }
    }
    suspend fun getCatatan(id: Long): Karyawan? {
        return dao.getKaryawanById(id)
    }

    fun update(id: Long, judul: String, isi: String, absen: String) {
        val karyawan = Karyawan(
            id = id,
            tanggal = formatter.format(Date()),
            nama = judul,
            data_diri = isi,
            absen = absen,
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(karyawan)
            }
        }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
            }
        }
    }
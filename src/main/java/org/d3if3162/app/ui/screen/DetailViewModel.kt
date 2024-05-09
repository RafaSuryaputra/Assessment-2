package org.d3if3162.app.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3162.app.database.CatatanDao
import org.d3if3162.app.model.Catatan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: CatatanDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(judul: String, isi: String) {
        val catatan = Catatan(
            tanggal = formatter.format(Date()),
            judul = judul,
            catatan = isi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }
    suspend fun getCatatan(id: Long): Catatan? {
        return dao.getCatatanById(id)
    }

    fun update(id: Long, judul: String, isi: String) {
        val catatan = Catatan(
            id = id,
            tanggal = formatter.format(Date()),
            judul = judul,
            catatan = isi
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(catatan)
            }
        }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
            }
        }
    }
package org.d3if3162.app.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3162.app.database.KaryawanDao
import org.d3if3162.app.model.Karyawan

class MainViewModel(dao: KaryawanDao) : ViewModel() {
    val data: StateFlow<List<Karyawan>> = dao.getKaryawan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
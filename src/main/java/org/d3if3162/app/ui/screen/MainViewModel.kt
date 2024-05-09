package org.d3if3162.app.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3162.app.database.CatatanDao
import org.d3if3162.app.model.Catatan

class MainViewModel(dao: CatatanDao) : ViewModel() {
    val data: StateFlow<List<Catatan>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
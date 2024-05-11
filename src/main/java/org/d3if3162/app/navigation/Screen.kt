package org.d3if3162.app.navigation

import org.d3if3162.app.ui.screen.KEY_ID_KARYAWAN

sealed class Screen(val route: String ) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_KARYAWAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object About: Screen("aboutScreen")
}
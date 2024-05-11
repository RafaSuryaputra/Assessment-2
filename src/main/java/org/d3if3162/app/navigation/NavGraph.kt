package org.d3if3162.app.navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3162.app.ui.screen.AboutScreen
import org.d3if3162.app.ui.screen.DetailScreen
import org.d3if3162.app.ui.screen.KEY_ID_KARYAWAN
import org.d3if3162.app.ui.screen.KaryawanScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Karyawan.route)   {
            KaryawanScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_KARYAWAN) {type = NavType.LongType}
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_KARYAWAN)
            DetailScreen(navController, id)
        }
    }
}
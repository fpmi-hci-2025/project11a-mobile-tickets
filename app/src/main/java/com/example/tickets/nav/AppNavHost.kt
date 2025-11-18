package com.example.tickets.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tickets.ui.MainScreen
import com.example.tickets.ui.PlaceholderScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("search") { PlaceholderScreen("Поиск") }
        composable("support") { PlaceholderScreen("Техподдержка") }
        composable("promo") { PlaceholderScreen("Все акции") }
        composable("promo_1") { PlaceholderScreen("Скидка 10%") }
        composable("promo_2") { PlaceholderScreen("2 билета по цене 1") }
    }
}

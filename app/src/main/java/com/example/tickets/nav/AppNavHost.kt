package com.example.tickets.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tickets.data.DatabaseModule
import com.example.tickets.ui.CheckoutScreen
import com.example.tickets.ui.ComfortAndPaymentScreen
import com.example.tickets.ui.ConfirmationScreen
import com.example.tickets.ui.LoginScreen
import com.example.tickets.ui.MainScreen
import com.example.tickets.ui.PlaceholderScreen
import com.example.tickets.ui.ProfileScreen
import com.example.tickets.ui.PromoDetailsScreen
import com.example.tickets.ui.PromoGridScreen
import com.example.tickets.ui.RegisterScreen
import com.example.tickets.ui.SearchTicketsScreen
import com.example.tickets.ui.SupportChatScreen
import com.example.tickets.ui.SupportScreen
import com.example.tickets.ui.TicketDetailsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val authManager = DatabaseModule.getAuthManager()
    var isLoggedIn by remember { mutableStateOf(authManager.isLoggedIn()) }
    var startDestination by remember { mutableStateOf(if (isLoggedIn) "main" else "login") }
    
    // Проверяем состояние авторизации при запуске
    LaunchedEffect(Unit) {
        isLoggedIn = authManager.isLoggedIn()
        startDestination = if (isLoggedIn) "main" else "login"
    }
    
    NavHost(navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("search/{from}/{to}/{dateStart}/{dateEnd}") { backStackEntry ->
            val from = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("from") ?: "", "UTF-8")
            val to = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("to") ?: "", "UTF-8")
            val dateStart = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("dateStart") ?: "", "UTF-8")
            val dateEnd = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("dateEnd") ?: "", "UTF-8")
            SearchTicketsScreen(from, to, dateStart, dateEnd, navController)
        }
        composable("search") { SearchTicketsScreen("", "", "", "", navController) }
        composable("support") { SupportScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("promo") {  PromoGridScreen(navController)  }
        composable("support_chat") { SupportChatScreen(navController) }
        composable("checkout/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")?.toLongOrNull() ?: -1L
            CheckoutScreen(routeId, navController)
        }
        composable("comfort_and_payment/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")?.toLongOrNull() ?: -1L
            ComfortAndPaymentScreen(routeId, navController)
        }
        composable("confirmation_screen") { ConfirmationScreen(navController) }


        composable(
            route = "promo_details/{title}/{code}",
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val code = backStackEntry.arguments?.getString("code") ?: ""
            PromoDetailsScreen(title, code, navController)
        }
        composable(
            "ticket_details/{routeId}",
        ) { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")?.toLongOrNull() ?: -1L
            TicketDetailsScreen(routeId, navController)
        }

    }
}

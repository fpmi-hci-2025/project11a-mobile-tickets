package com.example.tickets.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tickets.ui.CheckoutScreen
import com.example.tickets.ui.ComfortAndPaymentScreen
import com.example.tickets.ui.ConfirmationScreen
import com.example.tickets.ui.MainScreen
import com.example.tickets.ui.PlaceholderScreen
import com.example.tickets.ui.ProfileScreen
import com.example.tickets.ui.PromoDetailsScreen
import com.example.tickets.ui.PromoGridScreen
import com.example.tickets.ui.SearchTicketsScreen
import com.example.tickets.ui.SupportChatScreen
import com.example.tickets.ui.SupportScreen
import com.example.tickets.ui.TicketDetailsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("search") { SearchTicketsScreen(navController) }
        composable("support") { SupportScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("promo") {  PromoGridScreen(navController)  }
        composable("support_chat") { SupportChatScreen(navController) }
        composable("checkout") { CheckoutScreen(navController) }
        composable("comfort_and_payment") { ComfortAndPaymentScreen(navController) }
        composable("confirmation_screen") { ConfirmationScreen(navController) }


        composable(
            route = "promo_details/{title}/{code}",
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val code = backStackEntry.arguments?.getString("code") ?: ""
            PromoDetailsScreen(title, code, navController)
        }
        composable(
            "ticket_details/{route}/{date}/{price}",
        ) { backStackEntry ->
            val route = backStackEntry.arguments?.getString("route") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val price = backStackEntry.arguments?.getString("price") ?: ""
            TicketDetailsScreen(route, date, price, navController)
        }

    }
}

package com.example.tickets

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.example.tickets.data.DatabaseInitializer
import com.example.tickets.data.DatabaseModule
import com.example.tickets.nav.AppNavHost
import com.example.tickets.ui.theme.TicketsTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize database
        DatabaseModule.initialize(applicationContext)
        
        // Fill database with initial data
        DatabaseInitializer.initializeDatabase(applicationContext)
        
        setContent {
            TicketsTheme {
                AppNavHost(rememberNavController())
            }
        }
    }
}

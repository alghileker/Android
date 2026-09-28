package com.example.alghiproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alghiproject.data.FinanceRepository
import com.example.alghiproject.data.UserPreferencesRepository
import com.example.alghiproject.data.local.AppDatabase
import com.example.alghiproject.ui.dashboard.*
import com.example.alghiproject.ui.settings.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alghiproject.ui.navigation.Screen
import com.example.alghiproject.ui.navigation.bottomNavItems
import com.example.alghiproject.ui.theme.AlghiProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlghiProjectTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val context = LocalContext.current
    val userPrefsRepository = remember { UserPreferencesRepository(context) }
    val database = remember { AppDatabase.getDatabase(context) }
    val financeRepository = remember { FinanceRepository(database.financeDao()) }

    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(userPrefsRepository)
    )
    val financeViewModel: FinanceViewModel = viewModel(
        factory = FinanceViewModelFactory(financeRepository)
    )

    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                DashboardScreen(
                    viewModel = financeViewModel,
                    onAddTransaction = { navController.navigate(Screen.AddTransaction.route) },
                    onSeeAllGoals = {
                        selectedItem = 1
                        navController.navigate(Screen.Goals.route)
                    }
                )
            }
            composable(Screen.Goals.route) {
                SavingsGoalScreen(
                    viewModel = financeViewModel,
                    onAddGoal = { navController.navigate(Screen.AddEditGoal.route) }
                )
            }
            composable(Screen.Analytics.route) {
                AnalyticsScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onManageCategories = { navController.navigate(Screen.ManageCategories.route) }
                )
            }
            composable(Screen.AddTransaction.route) {
                AddTransactionScreen(
                    viewModel = financeViewModel,
                    onDismiss = { navController.popBackStack() }
                )
            }
            composable(Screen.AddEditGoal.route) {
                AddEditSavingsGoalScreen(
                    viewModel = financeViewModel,
                    onDismiss = { navController.popBackStack() }
                )
            }
            composable(Screen.ManageCategories.route) {
                ManageCategoriesScreen(
                    viewModel = financeViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}



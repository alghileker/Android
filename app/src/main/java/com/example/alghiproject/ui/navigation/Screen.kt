package com.example.alghiproject.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Goals : Screen("goals", "Target", Icons.Default.List)
    object Analytics : Screen("analytics", "Laporan", Icons.Default.PieChart)
    object Settings : Screen("settings", "Pengaturan", Icons.Default.Settings)
    object AddTransaction : Screen("add_transaction", "Tambah", Icons.Default.Add)
    object AddEditGoal : Screen("add_edit_goal", "Target", Icons.Default.Add)
    object ManageCategories : Screen("manage_categories", "Kategori", Icons.Default.Category)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Goals,
    Screen.Analytics,
    Screen.Settings
)

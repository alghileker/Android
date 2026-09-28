package com.example.alghiproject.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object IconUtils {
    fun getIcon(name: String?): ImageVector {
        return when (name?.lowercase()) {
            "restaurant", "makan", "minum" -> Icons.Default.Restaurant
            "commute", "transport", "transportasi" -> Icons.Default.Commute
            "shopping_cart", "belanja" -> Icons.Default.ShoppingCart
            "payments", "gaji" -> Icons.Default.Payments
            "redeem", "bonus" -> Icons.Default.Redeem
            "home", "tagihan" -> Icons.Default.Home
            "movie", "hiburan" -> Icons.Default.Movie
            "medical_services", "kesehatan" -> Icons.Default.MedicalServices
            "school", "pendidikan" -> Icons.Default.School
            "savings", "tabungan" -> Icons.Default.Savings
            else -> Icons.Default.Category
        }
    }
}

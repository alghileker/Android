package com.example.alghiproject.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {
    fun formatRupiah(amount: String): String {
        val cleanString = amount.replace(Regex("[^\\d]"), "")
        if (cleanString.isEmpty()) return "0"
        
        val parsed = cleanString.toDoubleOrNull() ?: 0.0
        val formatter = NumberFormat.getInstance(Locale("in", "ID"))
        return formatter.format(parsed)
    }

    fun formatRupiah(amount: Double): String {
        val formatter = NumberFormat.getInstance(Locale("in", "ID"))
        return formatter.format(amount)
    }
}

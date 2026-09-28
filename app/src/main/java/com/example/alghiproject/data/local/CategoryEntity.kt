package com.example.alghiproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CategoryType {
    INCOME, EXPENSE
}

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val iconName: String,
    val colorHex: String,
    val type: CategoryType,
    val isDefault: Boolean = false
)

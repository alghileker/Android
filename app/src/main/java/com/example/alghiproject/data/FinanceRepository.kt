package com.example.alghiproject.data

import com.example.alghiproject.data.local.*
import kotlinx.coroutines.flow.Flow

class FinanceRepository(private val dao: FinanceDao) {

    val allCategories: Flow<List<CategoryEntity>> = dao.getAllCategories()
    val recentTransactions: Flow<List<TransactionEntity>> = dao.getRecentTransactions()
    val allTransactions: Flow<List<TransactionEntity>> = dao.getAllTransactions()
    val allSavingsGoals: Flow<List<SavingsGoalEntity>> = dao.getAllSavingsGoals()

    suspend fun insertTransaction(transaction: TransactionEntity) {
        dao.insertTransaction(transaction)
    }

    suspend fun insertCategory(category: CategoryEntity) {
        dao.insertCategory(category)
    }

    suspend fun insertSavingsGoal(goal: SavingsGoalEntity) {
        dao.insertSavingsGoal(goal)
    }

    suspend fun updateSavingsGoal(goal: SavingsGoalEntity) {
        dao.updateSavingsGoal(goal)
    }

    suspend fun deleteSavingsGoal(goal: SavingsGoalEntity) {
        dao.deleteSavingsGoal(goal)
    }

    suspend fun updateCategory(category: CategoryEntity) {
        dao.updateCategory(category)
    }

    suspend fun deleteCategory(category: CategoryEntity) {
        dao.deleteCategory(category)
    }

    suspend fun seedDefaultCategories() {
        val defaults = listOf(
            CategoryEntity(name = "Makan & Minum", iconName = "restaurant", colorHex = "#FF5722", type = CategoryType.EXPENSE, isDefault = true),
            CategoryEntity(name = "Transportasi", iconName = "commute", colorHex = "#2196F3", type = CategoryType.EXPENSE, isDefault = true),
            CategoryEntity(name = "Belanja", iconName = "shopping_cart", colorHex = "#9C27B0", type = CategoryType.EXPENSE, isDefault = true),
            CategoryEntity(name = "Tagihan", iconName = "home", colorHex = "#607D8B", type = CategoryType.EXPENSE, isDefault = true),
            CategoryEntity(name = "Gaji", iconName = "payments", colorHex = "#4CAF50", type = CategoryType.INCOME, isDefault = true),
            CategoryEntity(name = "Bonus", iconName = "redeem", colorHex = "#FFC107", type = CategoryType.INCOME, isDefault = true)
        )
        // Only seed if empty - simplified for now
        defaults.forEach { dao.insertCategory(it) }
    }
}

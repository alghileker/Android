package com.example.alghiproject.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alghiproject.data.FinanceRepository
import com.example.alghiproject.data.local.CategoryEntity
import com.example.alghiproject.data.local.CategoryType
import com.example.alghiproject.data.local.SavingsGoalEntity
import com.example.alghiproject.data.local.TransactionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FinanceViewModel(private val repository: FinanceRepository) : ViewModel() {

    val recentTransactions = repository.recentTransactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories = repository.allCategories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savingsGoals = repository.allSavingsGoals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalBalance: StateFlow<Double> = repository.allTransactions
        .map { transactions ->
            transactions.sumOf { if (it.type == CategoryType.INCOME) it.amount else -it.amount }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val incomeTotal: StateFlow<Double> = repository.allTransactions
        .map { transactions -> transactions.filter { it.type == CategoryType.INCOME }.sumOf { it.amount } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val expenseTotal: StateFlow<Double> = repository.allTransactions
        .map { transactions -> transactions.filter { it.type == CategoryType.EXPENSE }.sumOf { it.amount } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        viewModelScope.launch {
            repository.seedDefaultCategories()
        }
    }

    fun addTransaction(amount: Double, categoryId: Int, note: String, type: CategoryType) {
        viewModelScope.launch {
            repository.insertTransaction(
                TransactionEntity(
                    amount = amount,
                    categoryId = categoryId,
                    note = note,
                    date = System.currentTimeMillis(),
                    type = type
                )
            )
        }
    }

    fun addSavingsGoal(title: String, targetAmount: Double, currentAmount: Double, iconName: String, colorHex: String) {
        viewModelScope.launch {
            repository.insertSavingsGoal(
                SavingsGoalEntity(
                    title = title,
                    targetAmount = targetAmount,
                    currentAmount = currentAmount,
                    deadline = System.currentTimeMillis() + 31536000000L, // Default 1 year
                    iconName = iconName,
                    colorHex = colorHex
                )
            )
        }
    }

    fun updateSavingsGoal(goal: SavingsGoalEntity) {
        viewModelScope.launch {
            repository.updateSavingsGoal(goal)
        }
    }

    fun deleteSavingsGoal(goal: SavingsGoalEntity) {
        viewModelScope.launch {
            repository.deleteSavingsGoal(goal)
        }
    }

    fun addCategory(name: String, iconName: String, colorHex: String, type: CategoryType) {
        viewModelScope.launch {
            repository.insertCategory(
                CategoryEntity(name = name, iconName = iconName, colorHex = colorHex, type = type)
            )
        }
    }

    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }
}

class FinanceViewModelFactory(private val repository: FinanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.alghiproject.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alghiproject.data.UserPreferences
import com.example.alghiproject.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences> = repository.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreferences("Rp", ".", false, -1L, "SYSTEM")
        )

    fun updateTheme(theme: String) {
        viewModelScope.launch {
            repository.updateAppTheme(theme)
        }
    }

    fun toggleBiometric(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateBiometricEnabled(enabled)
        }
    }

    fun updateCurrency(symbol: String) {
        viewModelScope.launch {
            repository.updateCurrencySymbol(symbol)
        }
    }
}

class SettingsViewModelFactory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

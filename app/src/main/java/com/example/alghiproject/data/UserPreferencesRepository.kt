package com.example.alghiproject.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
        val THOUSAND_SEPARATOR = stringPreferencesKey("thousand_separator")
        val IS_BIOMETRIC_ENABLED = booleanPreferencesKey("is_biometric_enabled")
        val REMINDER_TIME = longPreferencesKey("reminder_time")
        val APP_THEME = stringPreferencesKey("app_theme")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val currencySymbol = preferences[PreferencesKeys.CURRENCY_SYMBOL] ?: "Rp"
            val thousandSeparator = preferences[PreferencesKeys.THOUSAND_SEPARATOR] ?: "."
            val isBiometricEnabled = preferences[PreferencesKeys.IS_BIOMETRIC_ENABLED] ?: false
            val reminderTime = preferences[PreferencesKeys.REMINDER_TIME] ?: -1L
            val appTheme = preferences[PreferencesKeys.APP_THEME] ?: "SYSTEM"
            UserPreferences(currencySymbol, thousandSeparator, isBiometricEnabled, reminderTime, appTheme)
        }

    suspend fun updateCurrencySymbol(symbol: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENCY_SYMBOL] = symbol
        }
    }

    suspend fun updateBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_BIOMETRIC_ENABLED] = enabled
        }
    }

    suspend fun updateAppTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.APP_THEME] = theme
        }
    }

    suspend fun updateReminderTime(timeMillis: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.REMINDER_TIME] = timeMillis
        }
    }
}

data class UserPreferences(
    val currencySymbol: String,
    val thousandSeparator: String,
    val isBiometricEnabled: Boolean,
    val reminderTime: Long,
    val appTheme: String
)

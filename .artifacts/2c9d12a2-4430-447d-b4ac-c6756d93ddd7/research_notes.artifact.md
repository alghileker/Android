# Research Notes: Settings Screen Implementation

## 1. Preference Schema (DataStore)

We will use `PreferencesDataStore` to store simple key-value pairs.

```kotlin
object PreferencesKeys {
    val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
    val THOUSAND_SEPARATOR = stringPreferencesKey("thousand_separator")
    val IS_BIOMETRIC_ENABLED = booleanPreferencesKey("is_biometric_enabled")
    val REMINDER_TIME = longPreferencesKey("reminder_time") // Timestamp or Minutes from midnight
    val APP_THEME = stringPreferencesKey("app_theme") // "LIGHT", "DARK", "SYSTEM"
}
```

## 2. Recommended Libraries

### Biometric Authentication
- **Dependency**: `androidx.biometric:biometric:1.2.0-alpha05`
- **Usage**: Use `BiometricPrompt` for a consistent UI across Android versions.

### Background Tasks (Reminders)
- **Dependency**: `androidx.work:work-runtime-ktx:2.9.0`
- **Usage**: `PeriodicWorkRequest` with `WorkManager` to ensure the reminder is sent even if the app is closed.

### Data Management (Export)
- **CSV**: `com.opencsv:opencsv:5.7.1` for easy data mapping.
- **PDF**: `android.graphics.pdf.PdfDocument` (Native) for simple table-based reports without heavy external libraries.

## 3. Room Database for Custom Categories

```kotlin
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val iconRes: Int, // Resource ID or name
    val colorHex: String,
    val type: CategoryType // Enum: EXPENSE, INCOME
)
```

## 4. UI Structure (Compose)

We will use a modular approach for the Settings UI:

- `SettingsScreen`: Top-level Scaffold with `TopAppBar`.
- `PreferenceGroup`: A header and a list of preference items.
- `PreferenceItem`: A generic row with an icon, title, optional summary, and trailing widget (Switch, Arrow, Text).

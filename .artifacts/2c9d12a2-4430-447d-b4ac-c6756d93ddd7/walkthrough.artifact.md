# Project Walkthrough - Money Manager App

I have successfully resolved the SDK compatibility issues and implemented the core foundation of the app along with all major UI components.

## Key Accomplishments

### 1. Technical Infrastructure
- **SDK 37 Support**: Updated build configuration to use Android 37 while maintaining backward compatibility with Kotlin 2.1.0 and KSP.
- **Room Database**: Integrated Room with entities for `Transaction`, `Category`, and `SavingsGoal`.
- **DataStore**: Implemented modern preference storage for currency, theme, and security settings.
- **Security**: Added `BiometricHelper` for Fingerprint/Face Unlock integration.
- **Background Tasks**: Setup `WorkManager` for daily transaction reminders.

### 2. UI/UX Design (Material 3)
- **Dashboard**: Hero card for balance, horizontal scroll for goals, and list for recent transactions.
- **Settings Screen**: Organized preference groups with switches and action items.
- **Add Transaction**: Full-featured input form with a custom numeric keypad and type toggle.
- **Savings Goals**: Detailed progress cards with visual indicators.
- **Analytics**: Visualization of expenses (Donut chart mock) and detailed legends.

## Screenshots

````carousel
![Dashboard](/C:/Users/ASUS/AndroidStudioProjects/alghiproject/.artifacts/2c9d12a2-4430-447d-b4ac-c6756d93ddd7/dashboard.png)
<!-- slide -->
![Settings](/C:/Users/ASUS/AndroidStudioProjects/alghiproject/.artifacts/2c9d12a2-4430-447d-b4ac-c6756d93ddd7/settings.png)
<!-- slide -->
![Add Transaction](/C:/Users/ASUS/AndroidStudioProjects/alghiproject/.artifacts/2c9d12a2-4430-447d-b4ac-c6756d93ddd7/add_transaction.png)
````

> [!NOTE]
> The app is now ready for functional logic implementation (actual database inserts/reads) based on the provided UI screens.

## Next Steps
- Implement logic to save transactions to the Room database.
- Hook up the Settings dialogs for Theme and Currency selection.
- Implement CSV export logic in the `ExportDataUseCase`.

# Implementation Plan - UI/UX Refinement & Functional Logic

This plan aims to refine the "Money Manager" app by adding missing features (notes, filters), improving UX (currency formatting, quick actions), and ensuring a professional UI/UX using Jetpack Compose.

## User Review Required

> [!IMPORTANT]
> - **Platform Consistency**: Although "Flutter" was mentioned, I will continue using **Jetpack Compose** to maintain the current project's integrity, applying the same high-level UI/UX principles.
> - **Category Icons**: I will implement a helper to map category names (e.g., "Makan", "Transport") to specific Material Icons automatically.
> - **Currency Logic**: Formatting will happen in real-time as the user types on the custom keypad.

## Proposed Changes

### [Component: UI Utilities]

#### [NEW] [CurrencyUtils](file:///C:/Users/ASUS/AndroidStudioProjects/alghiproject/app/src/main/java/com/example/alghiproject/util/CurrencyUtils.kt)
Helper function to format strings or numbers into Rupiah format (e.g., `10000` -> `10.000`).

#### [NEW] [IconUtils](file:///C:/Users/ASUS/AndroidStudioProjects/alghiproject/app/src/main/java/com/example/alghiproject/util/IconUtils.kt)
Mapping utility to return `ImageVector` based on `iconName` string from the database.

---

### [Component: UI - Transactions & Keypad]

#### [MODIFY] [AddTransactionScreen](file:///C:/Users/ASUS/AndroidStudioProjects/alghiproject/app/src/main/java/com/example/alghiproject/ui/dashboard/AddTransactionScreen.kt)
- **Add Notes**: Insert an `OutlinedTextField` for optional "Keterangan".
- **Dynamic Icons**: Use `IconUtils` to show the correct icon for each category.
- **Currency Format**: Apply `CurrencyUtils` to the amount display.

---

### [Component: UI - Dashboard & Goals]

#### [MODIFY] [DashboardScreen](file:///C:/Users/ASUS/AndroidStudioProjects/alghiproject/app/src/main/java/com/example/alghiproject/ui/dashboard/DashboardScreen.kt)
- **Quick Action**: Add an "Add Fund" (+ icon) button to the `SavingsGoalCard`.
- **Dynamic Icons**: Ensure category icons in the "Recent Transactions" list are dynamic.

---

### [Component: UI - Analytics & Reports]

#### [MODIFY] [AnalyticsScreen](file:///C:/Users/ASUS/AndroidStudioProjects/alghiproject/app/src/main/java/com/example/alghiproject/ui/dashboard/AnalyticsScreen.kt)
- **Time Filters**: Add a `ScrollableTabRow` or `SegmentedButton` with (Daily, Weekly, Monthly).
- **Nominal in Legend**: Update `LegendItem` to display `Rp` values alongside percentages.

## Verification Plan

### Manual Verification
1.  **Currency Formatting**: Type `50000` and ensure it displays as `50.000` instantly.
2.  **Optional Notes**: Save a transaction with a note "Beli Kopi" and verify it appears in the Dashboard list.
3.  **Quick Savings**: Click the "+" on a Savings Goal card and verify it opens the transaction form (or a simplified deposit form).
4.  **Filters**: Toggle between Daily/Weekly/Monthly on the Analytics screen.

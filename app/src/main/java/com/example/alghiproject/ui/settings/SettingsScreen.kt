package com.example.alghiproject.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alghiproject.ui.components.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onManageCategories: () -> Unit
) {
    val userPreferences by viewModel.userPreferences.collectAsState()
    var showThemeDialog by remember { mutableStateOf(false) }

    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Pilih Tema") },
            text = {
                Column {
                    listOf("LIGHT" to "Terang", "DARK" to "Gelap", "SYSTEM" to "Ikuti Sistem").forEach { (key, label) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateTheme(key)
                                    showThemeDialog = false
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = userPreferences.appTheme == key, onClick = null)
                            Spacer(Modifier.width(8.dp))
                            Text(label)
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showThemeDialog = false }) { Text("Tutup") } }
        )
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Pengaturan") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item { PreferenceHeader("Preferensi") }
            item {
                PreferenceItem(
                    title = "Tema Aplikasi",
                    summary = when (userPreferences.appTheme) {
                        "LIGHT" -> "Terang"
                        "DARK" -> "Gelap"
                        else -> "Ikuti Sistem"
                    },
                    icon = Icons.Default.Brightness6,
                    onClick = { showThemeDialog = true }
                )
            }
            item {
                PreferenceItem(
                    title = "Mata Uang",
                    summary = userPreferences.currencySymbol,
                    icon = Icons.Default.Payments,
                    onClick = { /* TODO */ }
                )
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
            item { PreferenceHeader("Kelola Data") }
            item {
                PreferenceItem(
                    title = "Manajemen Kategori",
                    summary = "Tambah atau edit kategori kustom",
                    icon = Icons.Default.Category,
                    onClick = onManageCategories
                )
            }
            item {
                PreferenceItem(
                    title = "Ekspor Data (CSV)",
                    summary = "Simpan riwayat transaksi ke file",
                    icon = Icons.Default.FileDownload,
                    onClick = { /* TODO */ }
                )
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
            item { PreferenceHeader("Keamanan") }
            item {
                SwitchPreferenceItem(
                    title = "Kunci Biometrik",
                    summary = "Gunakan sidik jari atau wajah",
                    icon = Icons.Default.Fingerprint,
                    checked = userPreferences.isBiometricEnabled,
                    onCheckedChange = { viewModel.toggleBiometric(it) }
                )
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
            item { PreferenceHeader("Lainnya") }
            item {
                PreferenceItem(
                    title = "Reset Data",
                    summary = "Hapus semua data aplikasi",
                    icon = Icons.Default.DeleteForever,
                    onClick = { /* TODO: Show Confirmation */ },
                    titleColor = MaterialTheme.colorScheme.error
                )
            }
            item {
                PreferenceItem(
                    title = "Tentang Aplikasi",
                    summary = "Versi 1.0.0",
                    icon = Icons.Default.Info,
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
fun PreferenceHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PreferenceItem(
    title: String,
    summary: String,
    icon: ImageVector,
    onClick: () -> Unit,
    titleColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (titleColor == MaterialTheme.colorScheme.error) titleColor else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 16.sp, color = titleColor)
            Text(text = summary, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SwitchPreferenceItem(
    title: String,
    summary: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp)
            Text(text = summary, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

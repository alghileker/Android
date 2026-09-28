package com.example.alghiproject.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alghiproject.util.CurrencyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen() {
    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text("Laporan") })
        }
    ) { padding ->
        var selectedTabIndex by remember { androidx.compose.runtime.mutableIntStateOf(2) } // Default Monthly
        val periods = listOf("Harian", "Mingguan", "Bulanan")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = androidx.compose.ui.graphics.Color.Transparent,
                divider = {}
            ) {
                periods.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, fontSize = 12.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Breakdown Pengeluaran", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(32.dp))
            
            // Mock Chart Placeholder
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 0.7f },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 24.dp
                )
                Text(text = "Makan\n35%", style = MaterialTheme.typography.labelLarge)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Legend
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                LegendItem("Makan & Minum", "35%", 850000.0, MaterialTheme.colorScheme.primary)
                LegendItem("Transportasi", "20%", 480000.0, MaterialTheme.colorScheme.secondary)
                LegendItem("Belanja", "15%", 360000.0, MaterialTheme.colorScheme.tertiary)
            }
        }
    }
}

@Composable
fun LegendItem(label: String, percent: String, amount: Double, color: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(12.dp), color = color, shape = androidx.compose.foundation.shape.CircleShape) {}
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Rp ${CurrencyUtils.formatRupiah(amount)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(text = percent, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
    }
}

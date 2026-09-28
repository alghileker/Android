package com.example.alghiproject.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alghiproject.ui.components.bounceClick
import com.example.alghiproject.ui.dashboard.FinanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsGoalScreen(
    viewModel: FinanceViewModel,
    onAddGoal: () -> Unit
) {
    val goals by viewModel.savingsGoals.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text("Target Tabungan") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGoal) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Target")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = goals) { goal ->
                GoalLargeCard(goal)
            }
        }
    }
}

@Composable
fun GoalLargeCard(goal: com.example.alghiproject.data.local.SavingsGoalEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = goal.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rp ${com.example.alghiproject.util.CurrencyUtils.formatRupiah(goal.currentAmount)} / Rp ${com.example.alghiproject.util.CurrencyUtils.formatRupiah(goal.targetAmount)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            val progress = (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Progress: ${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

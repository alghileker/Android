package com.example.alghiproject.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alghiproject.data.local.SavingsGoalEntity
import com.example.alghiproject.ui.components.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSavingsGoalScreen(
    viewModel: FinanceViewModel,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var currentAmount by remember { mutableStateOf("0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Target") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Batal")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nama Target (misal: Beli Laptop)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = targetAmount,
                onValueChange = { targetAmount = it },
                label = { Text("Target Dana (Rp)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = currentAmount,
                onValueChange = { currentAmount = it },
                label = { Text("Tabungan Awal (Rp)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val target = targetAmount.toDoubleOrNull() ?: 0.0
                    val current = currentAmount.toDoubleOrNull() ?: 0.0
                    if (title.isNotBlank() && target > 0) {
                        viewModel.addSavingsGoal(
                            title = title,
                            targetAmount = target,
                            currentAmount = current,
                            iconName = "savings",
                            colorHex = "#2196F3"
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth().bounceClick(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Simpan Target")
            }
        }
    }
}

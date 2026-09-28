package com.example.alghiproject.ui.dashboard

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alghiproject.util.CurrencyUtils
import com.example.alghiproject.util.IconUtils
import com.example.alghiproject.data.local.CategoryEntity
import com.example.alghiproject.data.local.CategoryType
import com.example.alghiproject.ui.components.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: FinanceViewModel,
    onDismiss: () -> Unit
) {
    var amountText by remember { mutableStateOf("0") }
    var noteText by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }
    val categories by viewModel.categories.collectAsState()
    var selectedCategory by remember { mutableStateOf<CategoryEntity?>(null) }

    LaunchedEffect(categories, isExpense) {
        if (selectedCategory == null || selectedCategory?.type != (if (isExpense) CategoryType.EXPENSE else CategoryType.INCOME)) {
            selectedCategory = categories.firstOrNull { it.type == (if (isExpense) CategoryType.EXPENSE else CategoryType.INCOME) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Catatan") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Type Toggle
            TabRow(
                selectedTabIndex = if (isExpense) 0 else 1,
                containerColor = Color.Transparent,
                divider = {}
            ) {
                Tab(selected = isExpense, onClick = { isExpense = true }) {
                    Text("Pengeluaran", modifier = Modifier.padding(16.dp), color = if (isExpense) Color.Red else Color.Gray)
                }
                Tab(selected = !isExpense, onClick = { isExpense = false }) {
                    Text("Pemasukan", modifier = Modifier.padding(16.dp), color = if (!isExpense) Color.Green else Color.Gray)
                }
            }

            // Amount Display
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Nominal", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "Rp ${CurrencyUtils.formatRupiah(amountText)}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isExpense) Color.Red else Color.Green
                )
            }

            // Optional Note
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Keterangan (opsional)") },
                leadingIcon = { Icon(Icons.AutoMirrored.Filled.Notes, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // Category Selection
            Text(text = "Pilih Kategori", fontWeight = FontWeight.Bold)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories.filter { it.type == (if (isExpense) CategoryType.EXPENSE else CategoryType.INCOME) }) { category ->
                    CategoryIconItem(
                        category = category,
                        isSelected = selectedCategory?.id == category.id,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Keypad
            NumericKeypad(onValueChange = { amountText = it }, currentAmount = amountText)
            
            Button(
                onClick = {
                    val amountValue = amountText.toDoubleOrNull() ?: 0.0
                    selectedCategory?.let {
                        viewModel.addTransaction(
                            amount = amountValue,
                            categoryId = it.id,
                            note = if (noteText.isNotBlank()) noteText else it.name,
                            type = it.type
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick(),
                shape = RoundedCornerShape(12.dp),
                enabled = amountText != "0" && selectedCategory != null
            ) {
                Text("Simpan Transaksi")
            }
        }
    }
}

@Composable
fun CategoryIconItem(
    category: CategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.bounceClick(onClick)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = IconUtils.getIcon(category.iconName),
                contentDescription = null,
                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = category.name,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun NumericKeypad(onValueChange: (String) -> Unit, currentAmount: String) {
    // Simplified keypad logic
    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "000", "0", "DEL")
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        userScrollEnabled = false
    ) {
        items(keys) { key ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .bounceClick {
                        when (key) {
                            "DEL" -> if (currentAmount.length > 1) onValueChange(currentAmount.dropLast(1)) else onValueChange("0")
                            else -> if (currentAmount == "0") onValueChange(key) else if (currentAmount.length < 12) onValueChange(currentAmount + key)
                        }
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = key, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

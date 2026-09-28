package com.example.alghiproject.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alghiproject.data.local.SavingsGoalEntity
import com.example.alghiproject.data.local.TransactionEntity
import com.example.alghiproject.ui.components.bounceClick
import com.example.alghiproject.util.CurrencyUtils
import com.example.alghiproject.util.IconUtils
import com.example.alghiproject.ui.theme.*

@Composable
fun DashboardScreen(
    viewModel: FinanceViewModel,
    onAddTransaction: () -> Unit,
    onSeeAllGoals: () -> Unit
) {
    val balance by viewModel.totalBalance.collectAsState()
    val income by viewModel.incomeTotal.collectAsState()
    val expense by viewModel.expenseTotal.collectAsState()
    val transactions by viewModel.recentTransactions.collectAsState()
    val savingsGoals by viewModel.savingsGoals.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransaction,
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.bounceClick()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                HeaderSection()
            }
            item {
                BalanceHeroCard(balance, income, expense)
            }
            item {
                SavingsGoalSection(savingsGoals, onSeeAllGoals)
            }
            item {
                RecentTransactionsSection(transactions)
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column {
        Text(
            text = "Halo, Alghi!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Kelola keuanganmu hari ini.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun BalanceHeroCard(balance: Double, income: Double, expense: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "Total Saldo", color = Color.White.copy(alpha = 0.7f))
            Text(
                text = "Rp ${CurrencyUtils.formatRupiah(balance)}",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                FinanceSummaryItem("Pemasukan", "Rp ${CurrencyUtils.formatRupiah(income)}", IncomeGreen)
                FinanceSummaryItem("Pengeluaran", "Rp ${CurrencyUtils.formatRupiah(expense)}", ExpenseRed)
            }
        }
    }
}

@Composable
fun FinanceSummaryItem(label: String, amount: String, color: Color) {
    Column {
        Text(text = label, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        Text(text = amount, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun SavingsGoalSection(goals: List<SavingsGoalEntity>, onSeeAll: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Target Tabungan", fontWeight = FontWeight.Bold)
            TextButton(onClick = onSeeAll) {
                Text(text = "Lihat Semua")
            }
        }
        if (goals.isEmpty()) {
            Text(text = "Belum ada target.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(16.dp))
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(goals) { goal ->
                    SavingsGoalCard(goal)
                }
            }
        }
    }
}

@Composable
fun SavingsGoalCard(goal: SavingsGoalEntity) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .bounceClick(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SavingsBlue.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            IconUtils.getIcon(goal.iconName),
                            contentDescription = null,
                            tint = SavingsBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = goal.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                
                // Add Fund Quick Action
                IconButton(
                    onClick = { /* TODO: Quick Deposit Dialog */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = "Isi Tabungan",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            val progress = (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = SavingsBlue,
                trackColor = SavingsBlue.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${(progress * 100).toInt()}% tercapai",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RecentTransactionsSection(transactions: List<TransactionEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Transaksi Terakhir", fontWeight = FontWeight.Bold)
        if (transactions.isEmpty()) {
            Text(text = "Belum ada transaksi.", fontSize = 14.sp, color = Color.Gray)
        }
        transactions.forEach { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            // Find category to get icon
            Icon(
                IconUtils.getIcon(transaction.note), // Simple heuristic: use note as category hint if category lookup not here
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.note, fontWeight = FontWeight.Medium)
            Text(text = "Kategori", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        val prefix = if (transaction.type == com.example.alghiproject.data.local.CategoryType.EXPENSE) "-" else "+"
        val color = if (transaction.type == com.example.alghiproject.data.local.CategoryType.EXPENSE) ExpenseRed else IncomeGreen
        Text(text = "$prefix Rp ${CurrencyUtils.formatRupiah(transaction.amount)}", color = color, fontWeight = FontWeight.Bold)
    }
}

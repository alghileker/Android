package com.example.alghiproject.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alghiproject.data.local.CategoryEntity
import com.example.alghiproject.data.local.CategoryType
import com.example.alghiproject.ui.dashboard.FinanceViewModel
import com.example.alghiproject.util.IconUtils
import com.example.alghiproject.ui.components.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoriesScreen(
    viewModel: FinanceViewModel,
    onBack: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Kategori") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Add dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(categories) { category ->
                CategoryListItem(
                    category = category,
                    onDelete = { viewModel.deleteCategory(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryListItem(category: CategoryEntity, onDelete: () -> Unit) {
    ListItem(
        headlineContent = { Text(category.name) },
        supportingContent = { Text(if (category.type == CategoryType.EXPENSE) "Pengeluaran" else "Pemasukan") },
        leadingContent = {
            Icon(IconUtils.getIcon(category.iconName), contentDescription = null)
        },
        trailingContent = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus")
            }
        },
        modifier = Modifier.bounceClick()
    )
}

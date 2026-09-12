package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.theme.*

// Mock data model
data class MedicineResult(val pharmacyName: String, val isAvailable: Boolean, val distance: String, val lastUpdate: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineSearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    
    // Dynamic mock results based on search query
    val results = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            emptyList()
        } else {
            listOf(
                MedicineResult("صيدلية الشفاء", true, "1.2 كم", "منذ 10 دقائق"),
                MedicineResult("صيدلية الهدى", true, "3.5 كم", "منذ 25 دقيقة"),
                MedicineResult("صيدلية الأمل", false, "4.1 كم", "منذ ساعة")
            ).shuffled() // Shuffle for demo dynamism
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("البحث عن دواء", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("اكتب اسم الدواء (مثال: باراسيتامول)...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "بحث", tint = MaterialTheme.colorScheme.primary) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "مسح", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
            
            // Suggested chips
            AnimatedVisibility(visible = searchQuery.isEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("الأكثر بحثاً", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SuggestionChip(onClick = { searchQuery = "أموكسيسيلين" }, label = { Text("أموكسيسيلين") })
                        SuggestionChip(onClick = { searchQuery = "باراسيتامول" }, label = { Text("باراسيتامول") })
                        SuggestionChip(onClick = { searchQuery = "إيبوبروفين" }, label = { Text("إيبوبروفين") })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (searchQuery.isNotEmpty()) {
                Text(
                    text = "نتائج لـ: $searchQuery",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                
                Text(
                    text = "الصيدليات التي أبلغت عن توفر هذا الدواء:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                )

                LazyColumn(
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(results) { result ->
                        MedicinePharmacyCard(
                            pharmacyName = result.pharmacyName,
                            isAvailable = result.isAvailable,
                            distance = result.distance,
                            lastUpdate = result.lastUpdate,
                            onMapClick = { navController.navigate("map") }
                        )
                    }
                }
            } else {
                // Empty state illustration
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Medication, contentDescription = null, tint = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.size(100.dp).padding(bottom = 16.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "ابحث عن أي دواء",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "سنقوم بالبحث في جميع الصيدليات ضمن نطاقك الجغرافي لمعرفة توفر الدواء.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicinePharmacyCard(
    pharmacyName: String,
    isAvailable: Boolean,
    distance: String,
    lastUpdate: String,
    onMapClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = pharmacyName, style = MaterialTheme.typography.titleLarge)
                ContainerStatus(
                    text = if (isAvailable) "متوفر" else "غير متوفر",
                    color = if (isAvailable) StatusOpen else StatusUnavailable
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Straighten, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = distance, color = MaterialTheme.colorScheme.onSurface)
                }
                Text(text = "آخر تحديث: $lastUpdate", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onMapClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "عرض على الخريطة", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("عرض على الخريطة", fontWeight = FontWeight.Bold)
            }
        }
    }
}


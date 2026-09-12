package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnDutyScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var currentFilter by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var selectedGovernorate by remember { mutableStateOf("الكل") }
    
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "تحديد $currentFilter",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                val options = listOf("الكل", "إدلب", "حلب", "حماة", "اللاذقية")
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedGovernorate = option
                                showBottomSheet = false
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selectedGovernorate == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (selectedGovernorate == option) FontWeight.Bold else FontWeight.Normal
                        )
                        if (selectedGovernorate == option) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("الصيدليات المناوبة", fontWeight = FontWeight.Bold)
                        Text("جميع الصيدليات المناوبة المضافة إلى التطبيق", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            
            // Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChipWidget(
                    text = if (selectedGovernorate == "الكل") "المحافظة" else selectedGovernorate,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentFilter = "المحافظة"
                        showBottomSheet = true
                    }
                )
                FilterChipWidget(
                    text = "المنطقة",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentFilter = "المنطقة"
                        showBottomSheet = true
                    }
                )
                FilterChipWidget(
                    text = "التاريخ",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentFilter = "التاريخ"
                        showBottomSheet = true
                    }
                )
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = StatusInfo, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("الأقرب إليّ", color = StatusInfo, fontWeight = FontWeight.Bold)
            }
            
            LazyColumn(
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(5) {
                    Column {
                        PharmacyCard(
                            name = "صيدلية الشفاء",
                            region = "إدلب - معرة النعمان",
                            status = "مناوبة الآن",
                            statusColor = StatusOpen,
                            distance = "2.4 كم",
                            hours = "8:00 مساءً - 8:00 صباحًا",
                            onClick = { navController.navigate("pharmacy_detail/1") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "آخر تحديث: منذ 15 دقيقة",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChipWidget(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}

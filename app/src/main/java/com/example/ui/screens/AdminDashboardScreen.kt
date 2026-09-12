package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
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
fun AdminDashboardScreen(navController: NavController) {
    var isPharmacyOpen by remember { mutableStateOf(true) }
    var isOnDuty by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إدارة صيدليتي", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "عودة")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pharmacy Status Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "صيدلية الشفاء",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "دمشق - المزة",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Quick Status Toggles
            item {
                Text(
                    text = "الحالة الحالية",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                ) {
                    Column {
                        SwitchRow(
                            title = "الصيدلية مفتوحة الآن",
                            subtitle = "يظهر للعملاء أن الصيدلية تستقبل الزبائن",
                            icon = Icons.Default.Storefront,
                            checked = isPharmacyOpen,
                            onCheckedChange = { isPharmacyOpen = it }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        SwitchRow(
                            title = "مناوبة ليلية",
                            subtitle = "تفعيل في حال كانت الصيدلية مناوبة اليوم",
                            icon = Icons.Default.NightlightRound,
                            checked = isOnDuty,
                            onCheckedChange = { isOnDuty = it }
                        )
                    }
                }
            }

            // Management Actions
            item {
                Text(
                    text = "أدوات الإدارة",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                ) {
                    Column {
                        AdminActionRow(
                            icon = Icons.Default.Inventory,
                            title = "إدارة المخزون والأدوية",
                            tint = MaterialTheme.colorScheme.primary,
                            onClick = { /* Navigate to Inventory */ }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        AdminActionRow(
                            icon = Icons.Default.ReceiptLong,
                            title = "الطلبات الواردة (الوصفات)",
                            tint = StatusInfo,
                            onClick = { /* Navigate to Requests */ }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        AdminActionRow(
                            icon = Icons.Default.Schedule,
                            title = "أوقات الدوام الرسمي",
                            tint = MaterialTheme.colorScheme.secondary,
                            onClick = { /* Navigate to Schedule */ }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        AdminActionRow(
                            icon = Icons.Default.EditLocation,
                            title = "تحديث معلومات الموقع",
                            tint = StatusOpen,
                            onClick = { /* Navigate to Location */ }
                        )
                    }
                }
            }
            
            // Statistics Summary
            item {
                Text(
                    text = "ملخص الأداء",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(title = "البحث عن أدويتك", value = "124", modifier = Modifier.weight(1f))
                    StatCard(title = "طلبات الوصفات", value = "12", modifier = Modifier.weight(1f), valueColor = StatusInfo)
                }
            }
        }
    }
}

@Composable
fun SwitchRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier, valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.displaySmall, color = valueColor, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AdminActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    tint: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

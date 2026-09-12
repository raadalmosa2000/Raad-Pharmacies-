package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("حسابي", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.background, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("مستخدم جديد", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("+963 900 000 000", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            item {
                SettingsGroup(title = "الخدمات الشخصية") {
                    SettingsItem(icon = Icons.Default.LocationOn, title = "موقعي المفضل", onClick = { navController.navigate("region_selection") })
                    SettingsItem(icon = Icons.Default.Favorite, title = "الصيدليات المفضلة", onClick = { navController.navigate("favorites") })
                    SettingsItem(icon = Icons.Default.Alarm, title = "تذكير بمواعيد الدواء", onClick = { navController.navigate("reminders") })
                    SettingsItem(icon = Icons.Default.Medication, title = "الأدوية المحفوظة", onClick = { navController.navigate("saved_medicines") })
                    SettingsItem(icon = Icons.Default.History, title = "سجل البحث", onClick = { navController.navigate("search_history") })
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            item {
                SettingsGroup(title = "الإعدادات العامة") {
                    SettingsItem(icon = Icons.Default.Notifications, title = "الإشعارات", onClick = {})
                    SettingsItem(icon = Icons.Default.Language, title = "اللغة", onClick = {})
                    SettingsItem(icon = Icons.Default.Settings, title = "الإعدادات", onClick = {})
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            item {
                SettingsGroup(title = "للصيدليات") {
                    SettingsItem(
                        icon = Icons.Default.Storefront, 
                        title = "إضافة صيدليتي", 
                        onClick = { navController.navigate("register_pharmacy") }
                    )
                    SettingsItem(
                        icon = Icons.Default.Dashboard, 
                        title = "لوحة التحكم", 
                        onClick = { navController.navigate("admin_dashboard") },
                        tint = StatusInfo
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, onClick: () -> Unit, tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

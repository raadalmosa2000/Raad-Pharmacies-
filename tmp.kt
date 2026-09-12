package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "صيدلياتنا",
                style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = "اعثر على الصيدلية التي تحتاجها الآن",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                placeholder = { Text("ابحث عن دواء أو صيدلية...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "بحث", tint = MaterialTheme.colorScheme.primary) },
                trailingIcon = { 
                    IconButton(onClick = { navController.navigate("prescription_scanner") }) { 
                        Icon(Icons.Default.CameraAlt, contentDescription = "قراءة وصفة", tint = MaterialTheme.colorScheme.primary) 
                    } 
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ServiceCard(
                    title = "الصيدليات المناوبة",
                    subtitle = "اعرف الصيدليات المناوبة اليوم",
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocalHospital, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("onduty") }
                )
                ServiceCard(
                    title = "مفتوحة الآن",
                    subtitle = "الصيدليات المفتوحة حاليًا",
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusOpen)
                        }
                    },
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Filter */ }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ServiceCard(
                    title = "دوام متأخر",
                    subtitle = "الصيدليات التي تعمل حتى وقت متأخر",

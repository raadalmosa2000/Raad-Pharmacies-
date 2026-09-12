package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pharmacies")
data class PharmacyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val region: String,
    val status: String,
    val statusColor: Long, // Storing color as Long (ARGB)
    val distance: String,
    val hours: String
)

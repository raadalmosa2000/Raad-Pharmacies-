package com.example.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.PharmacyEntity
import com.example.data.PharmacyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PharmacyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PharmacyRepository
    val favoritePharmacies: StateFlow<List<PharmacyEntity>>

    init {
        val pharmacyDao = AppDatabase.getDatabase(application).pharmacyDao()
        repository = PharmacyRepository(pharmacyDao)
        favoritePharmacies = repository.allFavorites.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun isFavorite(id: Int): Flow<Boolean> = repository.isFavorite(id)

    fun toggleFavorite(id: Int, name: String, region: String, status: String, statusColor: Color, distance: String, hours: String, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyFavorite) {
                repository.removeFavorite(id)
            } else {
                repository.insertFavorite(
                    PharmacyEntity(
                        id = id,
                        name = name,
                        region = region,
                        status = status,
                        statusColor = statusColor.value.toLong(),
                        distance = distance,
                        hours = hours
                    )
                )
            }
        }
    }
}

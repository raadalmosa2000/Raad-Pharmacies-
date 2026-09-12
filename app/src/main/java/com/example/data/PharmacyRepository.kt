package com.example.data

import kotlinx.coroutines.flow.Flow

class PharmacyRepository(private val pharmacyDao: PharmacyDao) {
    val allFavorites: Flow<List<PharmacyEntity>> = pharmacyDao.getAllFavorites()

    fun isFavorite(id: Int): Flow<Boolean> = pharmacyDao.isFavorite(id)

    suspend fun insertFavorite(pharmacy: PharmacyEntity) = pharmacyDao.insertFavorite(pharmacy)

    suspend fun removeFavorite(id: Int) = pharmacyDao.deleteFavoriteById(id)
}

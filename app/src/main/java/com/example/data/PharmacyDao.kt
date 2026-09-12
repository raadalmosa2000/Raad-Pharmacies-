package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PharmacyDao {
    @Query("SELECT * FROM favorite_pharmacies")
    fun getAllFavorites(): Flow<List<PharmacyEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_pharmacies WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(pharmacy: PharmacyEntity)

    @Query("DELETE FROM favorite_pharmacies WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
}

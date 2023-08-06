package com.rizki.alfatest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteEntity)

    @Query("SELECT * FROM favouriteentity")
    fun getFavourite(): Flow<List<FavouriteEntity>>

    @Query("SELECT * FROM favouriteentity WHERE id = :id")
    suspend fun getFavouriteById(id: Int): FavouriteEntity?
}
package com.rizki.alfatest.app.data.local

import androidx.room.*
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteEntity)

    @Query("SELECT * FROM favouriteentity")
    fun getFavourite(): List<FavouriteEntity>

    @Query("SELECT * FROM favouriteentity WHERE id = :id")
    suspend fun getFavouriteById(id: Int?): FavouriteEntity?
}
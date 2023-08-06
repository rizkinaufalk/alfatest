package com.rizki.alfatest.domain.repository

import com.rizki.alfatest.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {

    fun getFavMovie(): Flow<List<FavouriteEntity>>

    suspend fun getFavMovieById(id: Int): FavouriteEntity?

    suspend fun insertFavMovie(favouriteEntity: FavouriteEntity)

    suspend fun deleteFavMovie(favouriteEntity: FavouriteEntity)
}